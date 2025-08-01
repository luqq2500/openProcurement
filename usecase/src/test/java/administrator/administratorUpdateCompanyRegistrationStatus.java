package administrator;

import address.CountryCode;
import administrator.api.CompanyRegistrationStatusUpdater;
import administrator.dto.UpdateCompanyRegistrationStatusCommand;
import company.CompanyRegistration;
import company.CompanyRegistrationStatus;
import company.CompanyStructure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class administratorUpdateCompanyRegistrationStatus {
    private List<CompanyRegistration> registrations;
    private List<Administrator> administrators;
    CompanyRegistrationStatusUpdater statusUpdater;

    @Before
    public void setUp() throws Exception {
        registrations = List.of(
                new CompanyRegistration("Terraform", "202380061600", "202580091900",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CountryCode.MY, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration("PetaByte", "220389997777", "777766664444",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration("PetaByte", "220389997766", "777766664400",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration("PetaByte", "220380007777", "777766660044",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.REJECTED)
        );
        administrators = List.of(
                new Administrator("Luqman", "Hakim", "hakimluqq25@gmail.com",
                        "123ss", AdministratorRoles.PROCUREMENT_ADMINISTRATOR),
                new Administrator("Walter", "White", "ww@gmail.com",
                        "234s", AdministratorRoles.SYSTEM_ADMINISTRATOR)
        );
        statusUpdater = new UpdateCompanyRegistrationStatus(()->registrations, ()->administrators);
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
    }

    @Test
    public void updateProcessingStatusToRejectedShouldNotThrowException() throws Exception {
        UpdateCompanyRegistrationStatusCommand command = new UpdateCompanyRegistrationStatusCommand(// random administrator id
                administrators.getFirst().getAdministratorId(),
                registrations.get(1).getRegistrationNumber(),
                CompanyRegistrationStatus.REJECTED,
                "All documents are eligible for approval."
        );
        statusUpdater.update(command);
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
