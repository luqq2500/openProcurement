package domain.registration;

import domain.address.Address;
import domain.address.Country;
import domain.company.Structure;
import domain.registration.exception.InvalidRegistrationApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class RegistrationApplicationApplicationDomainTest {
    RegistrationApplication initialRegistration;
    CompanyDetails newCompanyDetails;
    AccountAdministratorDetails newAccountAdministratorDetails;

    @Before
    public void setUp() throws Exception {
        initialRegistration = new RegistrationApplication(UUID.randomUUID(), UUID.randomUUID(),
                new CompanyDetails(",", new Address("1", "1", "1",
                                "1", "43900", "sel", Country.MALAYSIA), "2222", Structure.SOLE
                ),
                new AccountAdministratorDetails("1", "1", "username", "123"),
                RegistrationApplicationStatus.UNDER_REVIEW, LocalDateTime.now(), 1);
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
        RegistrationApplication approvedRegistration = initialRegistration.updateStatus(RegistrationApplicationStatus.APPROVED);
        RuntimeException exception = Assert.assertThrows(
                InvalidRegistrationApplication.class,
                ()-> approvedRegistration.changeDetails(newCompanyDetails, newAccountAdministratorDetails));
        System.out.println(exception.getMessage());
    }
    @Test
    public void changeDetails_versionAfterShouldPlusOne() {
        RegistrationApplication rejectedRegistration = initialRegistration.updateStatus(RegistrationApplicationStatus.REJECTED);
        RegistrationApplication changedRegistration = rejectedRegistration.changeDetails(newCompanyDetails, newAccountAdministratorDetails);
        Assert.assertEquals(rejectedRegistration.version()+1, changedRegistration.version());
    }
    @Test
    public void changeDetailsTwice_versionAfterShouldPlusTwo(){
        RegistrationApplication rejected1 = initialRegistration.updateStatus(RegistrationApplicationStatus.REJECTED);
        RegistrationApplication changeDetail1 = rejected1.changeDetails(newCompanyDetails, newAccountAdministratorDetails);
        RegistrationApplication rejected2 = changeDetail1.updateStatus(RegistrationApplicationStatus.REJECTED);
        RegistrationApplication changeDetail2 = rejected2.changeDetails(newCompanyDetails, newAccountAdministratorDetails);
        Assert.assertEquals(changeDetail1.version()+1, changeDetail2.version());
        System.out.println(changeDetail1.version());
        System.out.println(changeDetail2.version());
    }

    @Test
    public void updateValidStatus_versionShouldEqual(){
        RegistrationApplication rejectedRegistration = initialRegistration.updateStatus(RegistrationApplicationStatus.REJECTED);
        RegistrationApplication approvedRegistration = initialRegistration.updateStatus(RegistrationApplicationStatus.APPROVED);
        Assert.assertEquals(initialRegistration.version(), rejectedRegistration.version());
        Assert.assertEquals(initialRegistration.version(), approvedRegistration.version());
    }
    @Test
    public void invalidStatusUpdate_shouldThrowException() {
        RegistrationApplication rejectedRegistration = initialRegistration.updateStatus(RegistrationApplicationStatus.REJECTED);
        RegistrationApplication approvedRegistration = initialRegistration.updateStatus(RegistrationApplicationStatus.APPROVED);

        RuntimeException error1 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->initialRegistration.updateStatus(RegistrationApplicationStatus.UNDER_REVIEW));
        RuntimeException error2 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->rejectedRegistration.updateStatus(RegistrationApplicationStatus.REJECTED));
        RuntimeException error3 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->rejectedRegistration.updateStatus(RegistrationApplicationStatus.APPROVED));
        RuntimeException error4 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(RegistrationApplicationStatus.UNDER_REVIEW));
        RuntimeException error5 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(RegistrationApplicationStatus.APPROVED));
        RuntimeException error6 = Assert.assertThrows(InvalidRegistrationApplication.class, ()->approvedRegistration.updateStatus(RegistrationApplicationStatus.REJECTED));

        System.out.println(error1.getMessage());
        System.out.println(error2.getMessage());
        System.out.println(error3.getMessage());
        System.out.println(error4.getMessage());
        System.out.println(error5.getMessage());
        System.out.println(error6.getMessage());
    }
}
