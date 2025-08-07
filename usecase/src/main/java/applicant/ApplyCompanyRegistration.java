package applicant;

import applicant.api.CompanyRegistrationApplier;
import applicant.dto.ApplyCompanyRegistrationRequest;
import applicant.dto.ApplyCompanyRegistrationResponse;
import applicant.exception.CompanyRegistrationNumberNotApplicableForRegistration;
import applicant.exception.CompanyTaxNumberNotApplicableForRegistration;
import company.CompanyCountryRegistrationRule;
import company.CompanyRegistration;
import company.CompanyRegistrationRequest;
import company.CompanyRegistrationStatus;
import company.exception.InvalidCompanyRegistrationApplication;
import company.spi.CompanyCountryRegistrationRuleRepository;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRegistrationRequestRepository;
import ddd.DomainService;

import java.util.Optional;

@DomainService
public class ApplyCompanyRegistration implements CompanyRegistrationApplier {
    private final CompanyRegistrationRequestRepository requestRepository;
    private final CompanyRegistrationRepository repository;
    private final CompanyCountryRegistrationRuleRepository countryRegistrationRuleRepository;

    public ApplyCompanyRegistration(CompanyRegistrationRequestRepository requestRepository, CompanyRegistrationRepository repository, CompanyCountryRegistrationRuleRepository countryRegistrationRuleRepository) {
        this.requestRepository = requestRepository;
        this.repository = repository;
        this.countryRegistrationRuleRepository = countryRegistrationRuleRepository;
    }

    @Override
    public ApplyCompanyRegistrationResponse apply(ApplyCompanyRegistrationRequest command){
        validateRegistrationRequest(command);
        validateCountryRegistrationRule(command);
        checkRegistrationNumberIsApplicableForRegistration(command);
        checkTaxNumberIsApplicableForRegistration(command);
        CompanyRegistration registration = new CompanyRegistration(command.email(), command.companyName(), command.address(), command.registrationNumber(), command.taxNumber(), command.structure(), CompanyRegistrationStatus.PENDING);
        repository.add(registration);
        return new ApplyCompanyRegistrationResponse(command.email(), command.companyName(), command.address(), command.registrationNumber(), command.taxNumber(), command.structure());
    }

    private void validateRegistrationRequest(ApplyCompanyRegistrationRequest command) {
        CompanyRegistrationRequest request = requestRepository.get(command.requestId());
        request.checkValidity();
    }

    public void validateCountryRegistrationRule(ApplyCompanyRegistrationRequest command) {
        Optional<CompanyCountryRegistrationRule> rule = countryRegistrationRuleRepository.findByCountryCode(command.address().country());
        if (rule.isPresent()) {
            rule.get().validateRegistrationNumber(command.registrationNumber());
            rule.get().validateTaxNumber(command.taxNumber());
            rule.get().validateCompanyStructure(command.structure());
        }
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
