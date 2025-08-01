package company;

import company.api.CompanyRegistrationApplier;
import company.dto.ApplyCompanyRegistrationCommand;
import company.spi.CompanyCountryRegistrationRuleRepository;
import company.spi.CompanyRegistrationRepository;

public class ApplyCompanyRegistration implements CompanyRegistrationApplier {
    private final CompanyRegistrationRepository repository;
    private final CompanyCountryRegistrationRuleRepository countryRegistrationRuleRepository;

    public ApplyCompanyRegistration(CompanyRegistrationRepository repository, CompanyCountryRegistrationRuleRepository countryRegistrationRuleRepository) {
        this.repository = repository;
        this.countryRegistrationRuleRepository = countryRegistrationRuleRepository;
    }

    @Override
    public void apply(ApplyCompanyRegistrationCommand command){
        validateRegistrationNumber(command);
        validateTaxNumber(command);
        validateCountryRegistrationRule(command);
        CompanyRegistration registration = new CompanyRegistration(
                command.companyName(),
                command.registrationNumber(),
                command.taxNumber(),
                command.structure(),
                command.countryCode(),
                CompanyRegistrationStatus.PENDING
        );
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
