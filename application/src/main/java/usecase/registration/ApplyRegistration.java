package usecase.registration;

import annotation.DomainService;
import domain.account.Account;
import domain.registration.*;
import usecase.account.spi.AccountRepository;
import usecase.registration.api.RegistrationApplier;
import usecase.registration.dto.RegistrationDetails;
import usecase.registration.spi.RegistrationAdministrationRepository;
import usecase.registration.spi.RegistrationRepository;
import usecase.registration.exception.InvalidCompanyRegistration;

import java.util.Optional;


@DomainService
public class ApplyRegistration implements RegistrationApplier {
    private final AccountRepository accountRepository;
    private final RegistrationRepository registrationRepository;
    private final RegistrationAdministrationRepository administrationRepository;

    public ApplyRegistration(AccountRepository accountRepository, RegistrationRepository registrationRepository, RegistrationAdministrationRepository administrationRepository) {
        this.accountRepository = accountRepository;
        this.registrationRepository = registrationRepository;
        this.administrationRepository = administrationRepository;
    }

    @Override
    public void apply(RegistrationDetails details) {
        CompanyDetails companyDetails = new CompanyDetails(details.companyName(), details.brn(), details.structure());
        AccountAdminDetails accountAdminDetails = new AccountAdminDetails(details.firstName(), details.lastName(), details.email(), details.password());

        // validate information uniqueness: company name, business registration number, email.
        validateCompanyName(details.companyName());
        validateBrn(details.brn());
        validateEmail(details.email());

        Registration registration;
        Account account = accountRepository.get(details.accountId());

        // validate existing registration administration
        if (details.registrationId()!=null) {
            Optional<Registration> latestRegistration = registrationRepository.findLatestVersion(details.registrationId());
            Optional<RegistrationAdministration> latestAdministration = administrationRepository.findLatestVersion(details.registrationId());
            if (latestRegistration.isEmpty()) {
                throw new InvalidCompanyRegistration("Registration does not exist.");}
            if (latestAdministration.isEmpty()) {
                throw new InvalidCompanyRegistration("Existing registration has not been administered yet.");}
            if (latestAdministration.get().isApproved()) {
                throw new InvalidCompanyRegistration("Registration has already been approved.");}
            registration = account.resubmitRegistration(latestRegistration.get(), companyDetails, accountAdminDetails);
        } else {
            registration = account.register(companyDetails, accountAdminDetails);
        }
        registrationRepository.add(registration);
    }

    private void validateEmail(String email) {
        Optional<Registration> findUsedEmail = registrationRepository.findLatestByEmail(email);
        if (findUsedEmail.isPresent()) {
            Registration registration = findUsedEmail.get();
            Optional<RegistrationAdministration> findAdministration = administrationRepository.findLatestVersion(registration.registrationId());
            if (registration.getEmail().equals(email)
                    && findAdministration.isPresent()
                        && findAdministration.get().isApproved()) {
                throw new InvalidCompanyRegistration(email + " is already been used.");}
        }
    }

    private void validateBrn(String brn) {
        Optional<Registration> findUsedBrnInRegistration = registrationRepository.findLatestByBrn(brn);
        if (findUsedBrnInRegistration.isPresent()){
            Registration registration = findUsedBrnInRegistration.get();
            Optional<RegistrationAdministration> findAdministration = administrationRepository.findLatestVersion(registration.registrationId());
            if (registration.getBrn().equals(brn)
                    && findAdministration.isPresent()
                        && findAdministration.get().isApproved()){
                throw new InvalidCompanyRegistration(brn + " is already been used.");}}
    }

    private void validateCompanyName(String companyName) {
        Optional<Registration> findUsedCompanyNameInRegistration = registrationRepository.findLatestByCompanyName(companyName);
        if (findUsedCompanyNameInRegistration.isPresent()){
            Registration registration = findUsedCompanyNameInRegistration.get();
            Optional<RegistrationAdministration> findAdministration = administrationRepository.findLatestVersion(registration.registrationId());
            if (registration.getCompanyName().equals(companyName)
                    && findAdministration.isPresent()
                        && findAdministration.get().isApproved()){
                throw new InvalidCompanyRegistration(companyName + " is already been used.");}}
    }
}
