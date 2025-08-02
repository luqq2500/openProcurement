package administrator;

import address.CountryCode;
import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.dto.UpdateCompanyRegistrationStatusCommand;
import applicant.Applicant;
import company.Company;
import company.CompanyRegistration;
import company.CompanyRegistrationStatus;
import company.CompanyStructure;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class administratorUpdateCompanyRegistrationStatus {
    private List<CompanyRegistration> registrations;
    private List<Administrator> administrators;
    private CompanyRegistrationRepository companyRegistrationRepository;
    private CompanyRepository companyRepository;
    CompanyRegistrationStatusUpdater statusUpdater;

    @Before
    public void setUp() throws Exception {
        Applicant applicant = new Applicant("Luqman", "Hakim", "hakimluqq25@gmail.com", "0114305988");
        registrations = List.of(
                new CompanyRegistration("Terraform", "000000111111", "000000111111",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CountryCode.MY, applicant, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration("PetaByte", "000000222222", "000000222222",
                        CompanyStructure.SOLE, CountryCode.MY, applicant, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration("PetaByte", "000000333333", "000000333333",
                        CompanyStructure.SOLE, CountryCode.MY, applicant, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration("PetaByte", "000000444444", "000000444444",
                        CompanyStructure.SOLE, CountryCode.MY, applicant, CompanyRegistrationStatus.REJECTED)
        );
        administrators = List.of(
                new Administrator("Luqman", "Hakim", "hakimluqq25@gmail.com",
                        "123ss", AdministratorRoles.PROCUREMENT_ADMINISTRATOR),
                new Administrator("Walter", "White", "ww@gmail.com",
                        "234s", AdministratorRoles.SYSTEM_ADMINISTRATOR)
        );
        List<Company> companies = List.of(
                new Company("Terra", "202399996666", "202399994444", CompanyStructure.PUBLIC_LIMITED_COMPANY, CountryCode.MY)
        );
        companyRepository = new InMemoryCompanyRepository();
        companyRegistrationRepository = new InMemoryCompanyRegistrationRepository();
        companyRepository.addListOf(companies);
        companyRegistrationRepository.addListOf(registrations);
        statusUpdater = new UpdateCompanyRegistrationStatus(companyRegistrationRepository, ()->administrators, companyRepository);
    }

    @Test
    public void validAdministratorRole_shouldNotThrowException() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                administrators.getFirst().getAdministratorId(),
                registrations.getFirst().getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        statusUpdater.update(command);
    }

    @Test
    public void invalidAdministratorRole_shouldThrowException() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                administrators.get(1).getAdministratorId(),
                registrations.getFirst().getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> statusUpdater.update(command));
        System.out.println(error.getMessage());
    }

    @Test
    public void unregisteredAdministrator_shouldThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                "822ss", // random administrator id
                registrations.getFirst().getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> statusUpdater.update(command));
        System.out.println(error.getMessage());
    }

    @Test
    public void unregisteredCompanyRegistrationNumber_shouldThrowException() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                administrators.getFirst().getAdministratorId(),
                "20209920", // random registration number
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> statusUpdater.update(command));
        System.out.println(error.getMessage());
    }

    @Test
    public void pendingToProcessing_shouldNotThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.getFirst().getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        statusUpdater.update(command);
        Assert.assertEquals(companyRegistrationRepository.registrations().getLast().getRegistrationNumber(), command.companyRegistrationNumber());
        Assert.assertTrue(companyRegistrationRepository.registrations().getLast().getStatus().isProcessing());
    }

    @Test
    public void processingToApproved_registrationNumberBeforeAndAfterShouldEqual() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        statusUpdater.update(command);
        Assert.assertEquals(companyRegistrationRepository.registrations().getLast().getRegistrationNumber(), command.companyRegistrationNumber());
    }

    @Test
    public void processingToApproved_updatedRegistrationStatusShouldApproved(){
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        statusUpdater.update(command);
        Assert.assertTrue(companyRegistrationRepository.registrations().getLast().getStatus().isApproved());
    }

    @Test
    public void processingToApproved_registeredCompanyInRepositoryRegistrationNumberShouldEqual(){
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        statusUpdater.update(command);
        Assert.assertEquals(companyRegistrationRepository.registrations().getLast().getRegistrationNumber(), companyRepository.companies().getLast().getRegistrationNumber());
    }

    @Test
    public void processingToApproved_numberOfCompaniesInRepositoryShouldPlusOne(){
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        int previousCompanyRepositoryCompaniesSize = companyRepository.companies().size();
        statusUpdater.update(command);
        Assert.assertEquals(companyRepository.companies().size(), previousCompanyRepositoryCompaniesSize + 1);
    }

    @Test
    public void processingToReject_shouldNotThrowException() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.REJECTED,
                "Reject due to incomplete docs."
        );
        statusUpdater.update(command);
    }

    @Test
    public void processingToRejected_registrationNumberBeforeAndAfterShouldEqual() {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.REJECTED,
                "Reject due to incomplete docs."
        );
        statusUpdater.update(command);
        Assert.assertEquals(companyRegistrationRepository.registrations().getLast().getRegistrationNumber(), command.companyRegistrationNumber());
    }

    @Test
    public void processingToRejected_updatedStatusShouldBeRejected(){
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
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
                registrations.getFirst().getRegistrationNumber(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand pendingToReject = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.getFirst().getRegistrationNumber(),
                CompanyRegistrationStatus.REJECTED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand processingToProcessing = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand approveToProcessing = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(2).getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand approveToApprove = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(2).getRegistrationNumber(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand approveToReject = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(2).getRegistrationNumber(),
                CompanyRegistrationStatus.REJECTED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand rejectToProcessing = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(3).getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand rejectToApprove = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(3).getRegistrationNumber(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        UpdateCompanyRegistrationStatusCommand rejectToReject = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(3).getRegistrationNumber(),
                CompanyRegistrationStatus.REJECTED,
                "All documents are eligible for approval."
        );
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(pendingToApprove);});
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(pendingToReject);});
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(processingToProcessing);});
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(approveToProcessing);});
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(approveToApprove);});
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(approveToReject);});
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(rejectToProcessing);});
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(rejectToApprove);});
        Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(rejectToReject);});
    }
}
