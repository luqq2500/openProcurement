package administrator;

import address.CountryCode;
import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.dto.UpdateCompanyRegistrationStatusCommand;
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
    CompanyRegistrationStatusUpdater statusUpdater;

    @Before
    public void setUp() throws Exception {
        registrations = List.of(
                new CompanyRegistration("Terraform", "000000111111", "000000111111",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CountryCode.MY, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration("PetaByte", "000000222222", "000000222222",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration("PetaByte", "000000333333", "000000333333",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration("PetaByte", "000000444444", "000000444444",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.REJECTED)
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
        CompanyRepository companyRepository = new InMemoryCompanyRepository();
        companyRegistrationRepository = new InMemoryCompanyRegistrationRepository();
        companyRepository.addListOf(companies);
        companyRegistrationRepository.addListOf(registrations);
        statusUpdater = new UpdateCompanyRegistrationStatus(companyRegistrationRepository, ()->administrators, companyRepository);
    }

    @Test
    public void validAdministratorRoleProcessCompanyRegistrationShouldNotThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(
                administrators.getFirst().getAdministratorId(),
                registrations.getFirst().getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                null
        );
        statusUpdater.update(command);
    }

    @Test
    public void invalidAdministratorRoleProcessCompanyRegistrationShouldThrowException() throws Exception {
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
    public void processUnregisteredCompanyRegistrationShouldThrowException() throws Exception {
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
    public void unregisteredAdministratorProcessCompanyRegistrationShouldThrowException() throws Exception {
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
    public void updatePendingStatusToProcessingShouldNotThrowException() throws Exception {
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
    public void updateProcessingStatusToApprovedShouldNotThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.APPROVED,
                "All documents are eligible for approval."
        );
        statusUpdater.update(command);
        Assert.assertEquals(companyRegistrationRepository.registrations().getLast().getRegistrationNumber(), command.companyRegistrationNumber());
        Assert.assertTrue(companyRegistrationRepository.registrations().getLast().getStatus().isApproved());
    }

    @Test
    public void updateProcessingStatusToRejectedShouldNotThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.REJECTED,
                "Reject due to incomplete docs."
        );
        statusUpdater.update(command);
        Assert.assertEquals(companyRegistrationRepository.registrations().getLast().getRegistrationNumber(), command.companyRegistrationNumber());
        Assert.assertTrue(companyRegistrationRepository.registrations().getLast().getStatus().isRejected());
    }

    @Test
    public void updateInvalidStatusShouldThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.PROCESSING,
                "All documents are eligible for approval."
        );
        RuntimeException error = Assert.assertThrows(RuntimeException.class, ()-> {statusUpdater.update(command);});
        System.out.println(error.getMessage());
    }
}
