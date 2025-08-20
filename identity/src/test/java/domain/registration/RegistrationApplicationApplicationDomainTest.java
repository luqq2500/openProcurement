package domain.registration;

import domain.address.Address;
import domain.address.Country;
import domain.administrator.Administrator;
import domain.administrator.AdministratorRole;
import domain.company.Structure;
import domain.employee.PersonnelDetails;
import domain.registration.exception.InvalidRegistrationApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationApplicationApplicationDomainTest {
    RegistrationApplication initialRegistration;
    RegistrationApplication approvedRegistration;
    RegistrationApplication rejectedRegistration;
    CompanyDetails newCompanyDetails;
    AccountAdministratorDetails newAccountAdministratorDetails;
    Administrator identityAdministrator;
    Administrator nonIdentityAdministrator;

    @Before
    public void setUp() throws Exception {
        identityAdministrator = new Administrator(new PersonnelDetails("luqman", null), AdministratorRole.IDENTITY_ADMINISTRATOR);
        nonIdentityAdministrator = new Administrator(new PersonnelDetails("hakim", null), AdministratorRole.PROCUREMENT_ADMINISTRATOR);
        UUID registrationId = UUID.randomUUID();
        UUID requestId = UUID.randomUUID();
        initialRegistration = new RegistrationApplication(registrationId,
                new CompanyDetails(",", new Address("1", "1", "1",
                                "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE
                ),
                new AccountAdministratorDetails("1", "1", "username", "123"),
                RegistrationApplicationStatus.UNDER_REVIEW, LocalDateTime.now(), identityAdministrator.getAdministratorId(), requestId);
        approvedRegistration = initialRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.APPROVED);
        rejectedRegistration = initialRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.REJECTED);

        newCompanyDetails = new CompanyDetails(",", new Address("10", "1", "1", "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE);
        newAccountAdministratorDetails = new AccountAdministratorDetails("10", "1", "username", "123");
    }
    @Test
    public void changeDetailsWhenStatusUnderPreview_shouldThrowException() {
        RuntimeException exception = Assert.assertThrows(
                InvalidRegistrationApplication.class,
                ()-> initialRegistration.changeDetails(newCompanyDetails, newAccountAdministratorDetails));
        System.out.println(exception.getMessage());
    }

    @Test
    public void changeDetailsWhenRegistrationIsApproved_shouldThrowException() {
        RegistrationApplication approvedRegistration = initialRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.APPROVED);
        RuntimeException exception = Assert.assertThrows(
                InvalidRegistrationApplication.class,
                ()-> approvedRegistration.changeDetails(newCompanyDetails, newAccountAdministratorDetails));
        System.out.println(exception.getMessage());
    }

    @Test
    public void invalidAdministratorRole_shouldThrowException() {
        RuntimeException exception = Assert.assertThrows(InvalidRegistrationApplication.class,
                () -> initialRegistration.updateStatus(nonIdentityAdministrator, RegistrationApplicationStatus.REJECTED));
        System.out.println(exception.getMessage());
    }

    @Test
    public void invalidStatusUpdate_shouldThrowException() {
        RuntimeException error1 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->initialRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.UNDER_REVIEW));
        RuntimeException error2 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->rejectedRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.REJECTED));
        RuntimeException error3 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->rejectedRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.APPROVED));
        RuntimeException error4 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.UNDER_REVIEW));
        RuntimeException error5 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.APPROVED));
        RuntimeException error6 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(identityAdministrator, RegistrationApplicationStatus.REJECTED));
        System.out.println(error1.getMessage());
        System.out.println(error2.getMessage());
        System.out.println(error3.getMessage());
        System.out.println(error4.getMessage());
        System.out.println(error5.getMessage());
        System.out.println(error6.getMessage());
    }
}
