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
import java.util.Optional;

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
        CompanyRegistrationRequest request = getRegistrationRequest(command);
        request.checkRequestExpiry(LocalDateTime.now());
        validateRegistrationNumber(command);
        validateTaxNumber(command);
        CompanyCountryRegistrationRule rule = getCountryRegistrationRule(command);
        rule.validateRegistrationNumber(command.registrationNumber());
        rule.validateTaxNumber(command.taxNumber());
        rule.validateCompanyStructure(command.structure());
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

    private CompanyRegistrationRequest getRegistrationRequest(ApplyCompanyRegistrationCommand command) {
        Optional<CompanyRegistrationRequest> optionalRequest = requestRepository.findById(command.registrationRequestId());
        if (optionalRequest.isEmpty()){
            throw new RuntimeException("No request found for id " + command.registrationRequestId());
        }
        return optionalRequest.get();
    }

    public CompanyCountryRegistrationRule getCountryRegistrationRule(ApplyCompanyRegistrationCommand command) {
        Optional<CompanyCountryRegistrationRule> ruleOptional = countryRegistrationRuleRepository.findByCountryCode(command.countryCode());
        if (ruleOptional.isEmpty()){
            throw new RuntimeException("Company registration for country " + command.countryCode() + " is not available.");
        }
        return ruleOptional.get();
    }

    private void validateRegistrationNumber(ApplyCompanyRegistrationCommand command) {
        Optional<CompanyRegistration> registration = repository.findByCompanyRegistrationNumber(command.registrationNumber());
        if (registration.isPresent() && !registration.get().getStatus().isRejected()){
            throw new RuntimeException("Company with registration number " + command.registrationNumber() + " has already applied.");
        }
    }

    private void validateTaxNumber(ApplyCompanyRegistrationCommand command) {
        Optional<CompanyRegistration> registration = repository.findByTaxNumber(command.taxNumber());
        if (registration.isPresent() && !registration.get().getStatus().isRejected()){
            throw new RuntimeException("Company with tax number " + command.taxNumber() + " has already applied.");
        }
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
}
