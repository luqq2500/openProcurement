package administrator;

import address.Address;
import address.Country;
import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.exception.AdministratorNotFoundException;
import administrator.exception.AdministratorRoleInvalidException;
import administrator.spi.AdministratorRepository;
import company.*;
import company.exception.CompanyRegistrationRequestNotFound;
import company.exception.InvalidCompanyRegistrationStatus;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

public class administratorUpdateCompanyRegistrationStatus {
    private List<CompanyRegistration> registrations;
    private List<Administrator> administrators;
    private CompanyRegistrationRepository companyRegistrationRepository;
    private CompanyRepository companyRepository;
    private CompanyRegistrationStatusUpdater statusUpdater;

    @Before
    public void setUp(){
        String mockEmail = "hakimluqq25@gmail.com";
        Address mockAddress = new Address("1", "1", "1","Sepang", "43900", "Selangor", Country.MALAYSIA);
        registrations = List.of(
                new CompanyRegistration(mockEmail, "PetaByte", mockAddress,"000000111111", "000000111111",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration(mockEmail, "TerraForm",mockAddress,"000000222222", "000000222222",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration(mockEmail, "Citadel", mockAddress,"000000333333", "000000333333",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration(mockEmail, "PetaByte", mockAddress,"000000444444", "000000444444",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.REJECTED)
        );
        administrators = List.of(
                new Administrator("Luqman", "Hakim", "hakimluqq25@gmail.com",
                        "123ss", AdministratorRoles.PROCUREMENT_ADMINISTRATOR),
                new Administrator("Walter", "White", "ww@gmail.com",
                        "234s", AdministratorRoles.SYSTEM_ADMINISTRATOR)
        );
        List<Company> companies = List.of(
                new Company("Terra", "202399996666", "202399994444", CompanyStructure.PUBLIC_LIMITED_COMPANY, mockAddress)
        );
        companyRepository = new MockCompanyRepository();
        companyRegistrationRepository = new MockCompanyRegistrationRepository();
        AdministratorRepository administratorRepository = new MockAdministratorRepository();
        companies.forEach(companyRepository::add);
        registrations.forEach(companyRegistrationRepository::add);
        administrators.forEach(administratorRepository::add);
        statusUpdater = new UpdateCompanyRegistrationStatus(companyRegistrationRepository, administratorRepository, companyRepository);
    }

    @Test
    public void validAdministratorRole_shouldNotThrowException() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                administrators.getFirst().getAdministratorId(),
                registrations.getFirst().getId(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        statusUpdater.update(command);
    }

    @Test
    public void invalidAdministratorRole_shouldThrowException() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                administrators.get(1).getAdministratorId(),
                registrations.getFirst().getId(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        RuntimeException error = Assert.assertThrows(AdministratorRoleInvalidException.class, () -> statusUpdater.update(command));
        System.out.println(error.getMessage());
    }

    @Test
    public void unregisteredAdministrator_shouldThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                UUID.randomUUID(), // random administrator id
                registrations.getFirst().getId(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        RuntimeException error = Assert.assertThrows(AdministratorNotFoundException.class, () -> statusUpdater.update(command));
        System.out.println(error.getMessage());
    }

    @Test
    public void unregisteredCompanyRegistration_shouldThrowException() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                administrators.getFirst().getAdministratorId(),
                UUID.randomUUID(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        RuntimeException error = Assert.assertThrows(CompanyRegistrationRequestNotFound.class, () -> statusUpdater.update(command));
        System.out.println(error.getMessage());
    }

    @Test
    public void pendingToProcessing_shouldNotThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.getFirst().getId(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        statusUpdater.update(command);
        Assert.assertTrue(companyRegistrationRepository.registrations().getLast().getStatus().isProcessing());
    }

    @Test
    public void processingToApproved_updatedRegistrationStatusShouldApproved(){
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getId(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        statusUpdater.update(command);
        Assert.assertTrue(companyRegistrationRepository.registrations().getLast().getStatus().isApproved());
    }

    @Test
    public void processingToReject_shouldNotThrowException() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getId(),
                CompanyRegistrationStatus.REJECTED,
                "Reject due to incomplete docs."
        );
        statusUpdater.update(command);
    }

    @Test
    public void processingToRejected_updatedStatusShouldBeRejected(){
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getId(),
                CompanyRegistrationStatus.REJECTED,
                "Reject due to incomplete docs."
        );
        statusUpdater.update(command);
        Assert.assertTrue(companyRegistrationRepository.registrations().getLast().getStatus().isRejected());
    }

    @Test
    public void invalidStatusUpdate_shouldThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand pendingToApprove = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.getFirst().getId(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand pendingToReject = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.getFirst().getId(),
                CompanyRegistrationStatus.REJECTED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand processingToProcessing = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getId(),
                CompanyRegistrationStatus.PROCESSING,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand approveToProcessing = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(2).getId(),
                CompanyRegistrationStatus.PROCESSING,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand approveToApprove = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(2).getId(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand approveToReject = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(2).getId(),
                CompanyRegistrationStatus.REJECTED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand rejectToProcessing = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(3).getId(),
                CompanyRegistrationStatus.PROCESSING,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand rejectToApprove = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(3).getId(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand rejectToReject = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(3).getId(),
                CompanyRegistrationStatus.REJECTED,
                "All documents are eligible for approval."
        );
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(pendingToApprove);});
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(pendingToReject);});
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(processingToProcessing);});
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(approveToProcessing);});
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(approveToApprove);});
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(approveToReject);});
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(rejectToProcessing);});
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(rejectToApprove);});
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> {statusUpdater.update(rejectToReject);});
    }
}
