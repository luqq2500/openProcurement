package applicant;

import applicant.api.CompanyRegistrationApplier;
import applicant.dto.ApplyCompanyRegistrationCommand;
import company.CompanyCountryRegistrationRule;
import company.CompanyRegistration;
import company.CompanyRegistrationRequest;
import company.CompanyRegistrationStatus;
import company.spi.CompanyCountryRegistrationRuleRepository;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRegistrationRequestRepository;
import notification.NotificationService;
import notification.NotificationServiceCommand;

import java.time.LocalDateTime;

public class ApplyCompanyRegistration implements CompanyRegistrationApplier {
    private final CompanyRegistrationRepository repository;
    private final CompanyCountryRegistrationRuleRepository countryRegistrationRuleRepository;
    private final CompanyRegistrationRequestRepository requestRepository;
    private final NotificationService notificationService;

    public ApplyCompanyRegistration(CompanyRegistrationRepository repository, CompanyCountryRegistrationRuleRepository countryRegistrationRuleRepository, CompanyRegistrationRequestRepository requestRepository, NotificationService notificationService) {
        this.repository = repository;
        this.countryRegistrationRuleRepository = countryRegistrationRuleRepository;
        this.requestRepository = requestRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void apply(ApplyCompanyRegistrationCommand command){
        CompanyRegistrationRequest request = getCompanyRegistrationRequest(command);
        request.checkRequestExpiry(LocalDateTime.now());
        validateRegistrationNumber(command);
        validateTaxNumber(command);
        validateCountryRegistrationRule(command);
        CompanyRegistration registration = new CompanyRegistration(
                command.companyName(),
                command.registrationNumber(),
                command.taxNumber(),
                command.structure(),
                command.countryCode(),
                request.getApplicant(),
                CompanyRegistrationStatus.PENDING);
        repository.add(registration);
        NotificationServiceCommand notificationCommand = generateNotificationCommand(command, request, registration);
        notificationService.notify(notificationCommand);
    }

    private static NotificationServiceCommand generateNotificationCommand(ApplyCompanyRegistrationCommand command, CompanyRegistrationRequest request, CompanyRegistration registration) {
        String subject = "OpenProcurement Registration Submitted";
        String message = String.format(
                "Dear %s %s,\n\nYour company registration for %s is now pending review.\nRegistration ID: %s\n\nThank you,\nProcurement Team",
                request.getApplicant().firstName(),
                request.getApplicant().lastName(),
                command.companyName(),
                 registration.getRegistrationId()
        );
        return new NotificationServiceCommand(request.getApplicant().email(), subject, message);
    }

    private CompanyRegistrationRequest getCompanyRegistrationRequest(ApplyCompanyRegistrationCommand command) {
        return requestRepository.requests().stream()
                .filter(r -> r.getRequestId().equals(command.registrationRequestId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No company registration request found."));
    }

    private void validateCountryRegistrationRule(ApplyCompanyRegistrationCommand command) {
        CompanyCountryRegistrationRule rule = getCountryRegistrationRule(command);
        rule.validateRegistrationNumber(command.registrationNumber());
        rule.validateTaxNumber(command.taxNumber());
        rule.validateCompanyStructure(command.structure());
    }

    private CompanyCountryRegistrationRule getCountryRegistrationRule(ApplyCompanyRegistrationCommand command) {
        return countryRegistrationRuleRepository.rules().stream()
                .filter(r -> r.countryCode().equals(command.countryCode()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Country code " + command.countryCode() + " is not registered."));
    }

    private void validateRegistrationNumber(ApplyCompanyRegistrationCommand command) {
        if (repository.registrations().stream()
                .filter(registration -> !registration.getStatus().equals(CompanyRegistrationStatus.REJECTED))
                .anyMatch(registration -> registration.getRegistrationNumber().equals(command.registrationNumber()))) {
            throw new RuntimeException("Company registration number " + command.registrationNumber() + " already applied for registration.");
        }
    }

    private void validateTaxNumber(ApplyCompanyRegistrationCommand command) {
        if (repository.registrations().stream()
                .filter(registration -> !registration.getStatus().equals(CompanyRegistrationStatus.REJECTED))
                .anyMatch(registration -> registration.getTaxNumber().equals(command.taxNumber()))) {
            throw new RuntimeException("Tax number " + command.taxNumber() + " already applied for registration.");
        }
    }
}
