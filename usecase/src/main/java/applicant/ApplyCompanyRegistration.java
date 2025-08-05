package applicant;

import applicant.api.CompanyRegistrationApplier;
import applicant.dto.ApplyCompanyRegistrationRequest;
import applicant.dto.ApplyCompanyRegistrationResponse;
import applicant.exception.CompanyRegistrationNumberNotApplicableForRegistration;
import applicant.exception.CompanyTaxNumberNotApplicableForRegistration;
import company.CompanyCountryRegistrationRule;
import company.CompanyRegistration;
import company.CompanyRegistrationStatus;
import company.exception.CompanyCountryRegistrationRuleNotFound;
import company.spi.CompanyCountryRegistrationRuleRepository;
import company.spi.CompanyRegistrationRepository;
import ddd.DomainService;

import java.util.Optional;

@DomainService
public class ApplyCompanyRegistration implements CompanyRegistrationApplier {
    private final CompanyRegistrationRepository repository;
    private final CompanyCountryRegistrationRuleRepository countryRegistrationRuleRepository;

    public ApplyCompanyRegistration(CompanyRegistrationRepository repository, CompanyCountryRegistrationRuleRepository countryRegistrationRuleRepository) {
        this.repository = repository;
        this.countryRegistrationRuleRepository = countryRegistrationRuleRepository;
    }

    @Override
    public ApplyCompanyRegistrationResponse apply(ApplyCompanyRegistrationRequest command){
        validateCountryRegistrationRule(command);
        checkRegistrationNumberIsApplicableForRegistration(command);
        checkTaxNumberIsApplicableForRegistration(command);
        CompanyRegistration registration = new CompanyRegistration(
                command.email(),
                command.companyName(),
                command.address(),
                command.registrationNumber(),
                command.taxNumber(),
                command.structure(),
                CompanyRegistrationStatus.PENDING);
        repository.add(registration);
        return new ApplyCompanyRegistrationResponse(
                command.email(),
                command.companyName(),
                command.address(),
                command.registrationNumber(),
                command.taxNumber(),
                command.structure());
    }

    public void validateCountryRegistrationRule(ApplyCompanyRegistrationRequest command) {
        Optional<CompanyCountryRegistrationRule> optionalRule = countryRegistrationRuleRepository.findByCountryCode(command.address().country());
        if (optionalRule.isEmpty()){
            throw new CompanyCountryRegistrationRuleNotFound("Company registration for country " + command.address().country() + " is not available.");
        }
        CompanyCountryRegistrationRule rule = optionalRule.get();
        rule.validateRegistrationNumber(command.registrationNumber());
        rule.validateTaxNumber(command.taxNumber());
        rule.validateCompanyStructure(command.structure());
    }

    private void checkRegistrationNumberIsApplicableForRegistration(ApplyCompanyRegistrationRequest command) {
        if (repository.registrations().stream()
                .anyMatch(registration->registration.getRegistrationNumber().equals(command.registrationNumber())
                        && !registration.getStatus().isRejected())) {
            throw new CompanyRegistrationNumberNotApplicableForRegistration("Company with registration number " + command.registrationNumber() + " has already applied.");
        }
    }

    private void checkTaxNumberIsApplicableForRegistration(ApplyCompanyRegistrationRequest command) {
        if (repository.registrations().stream()
                .anyMatch(registration->registration.getTaxNumber().equals(command.taxNumber())
                        && !registration.getStatus().isRejected())){
            throw new CompanyTaxNumberNotApplicableForRegistration("Company with tax number " + command.taxNumber() + " has already applied.");
        }
    }
}
