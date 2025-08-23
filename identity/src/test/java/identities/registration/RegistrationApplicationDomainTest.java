package identities.registration;

import identities.address.Address;
import identities.address.Country;
import identities.company.Structure;
import registration.AccountAdministratorDetails;
import registration.CompanyDetails;
import registration.RegistrationApplication;
import registration.RegistrationStatus;
import registration.exception.InvalidRegistrationApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationApplicationDomainTest {
    RegistrationApplication initialRegistration;
    RegistrationApplication approvedRegistration;
    RegistrationApplication rejectedRegistration;
    CompanyDetails newCompanyDetails;
    AccountAdministratorDetails newAccountAdministratorDetails;
    UUID administratorId;


    @Before
    public void setUp() throws Exception {
        UUID requestId = UUID.randomUUID();
        administratorId = UUID.randomUUID();
        initialRegistration = new RegistrationApplication(requestId,
                new CompanyDetails(",", new Address("1", "1", "1",
                                "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE
                ),
                new AccountAdministratorDetails("1", "1", "username", "123"),
                RegistrationStatus.UNDER_REVIEW, LocalDateTime.now(), 1, requestId);
        approvedRegistration = initialRegistration.updateStatus(administratorId, RegistrationStatus.APPROVED);
        rejectedRegistration = initialRegistration.updateStatus(administratorId, RegistrationStatus.REJECTED);

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
        RegistrationApplication approvedRegistration = initialRegistration.updateStatus(administratorId, RegistrationStatus.APPROVED);
        RuntimeException exception = Assert.assertThrows(
                InvalidRegistrationApplication.class,
                ()-> approvedRegistration.changeDetails(newCompanyDetails, newAccountAdministratorDetails));
        System.out.println(exception.getMessage());
    }

    @Test
    public void changeDetails_versionShouldPlusOne(){
        RegistrationApplication changedRegistration = rejectedRegistration.changeDetails(newCompanyDetails, newAccountAdministratorDetails);
        Assert.assertEquals(initialRegistration.version()+1, changedRegistration.version());
    }

    @Test
    public void updateStatus_versionShouldRemainSame(){
        RegistrationApplication updatedRegistration = initialRegistration.updateStatus(administratorId, RegistrationStatus.APPROVED);
        Assert.assertEquals(initialRegistration.version(), updatedRegistration.version());
    }

    @Test
    public void invalidStatusUpdate_shouldThrowException() {
        RuntimeException error1 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->initialRegistration.updateStatus(administratorId, RegistrationStatus.UNDER_REVIEW));
        RuntimeException error2 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->rejectedRegistration.updateStatus(administratorId, RegistrationStatus.REJECTED));
        RuntimeException error3 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->rejectedRegistration.updateStatus(administratorId, RegistrationStatus.APPROVED));
        RuntimeException error4 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(administratorId, RegistrationStatus.UNDER_REVIEW));
        RuntimeException error5 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(administratorId, RegistrationStatus.APPROVED));
        RuntimeException error6 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(administratorId, RegistrationStatus.REJECTED));
        System.out.println(error1.getMessage());
        System.out.println(error2.getMessage());
        System.out.println(error3.getMessage());
        System.out.println(error4.getMessage());
        System.out.println(error5.getMessage());
        System.out.println(error6.getMessage());
    }
}
