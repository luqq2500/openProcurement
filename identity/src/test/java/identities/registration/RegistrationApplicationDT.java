package identities.registration;

import identities.address.Address;
import identities.address.Country;
import identities.company.Structure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class RegistrationApplicationDT {
    private RegistrationApplication registration;
    private CompanyDetails companyDetails;
    private AccountAdministratorDetails accountAdministratorDetails;

    @Before
    public void setUp() throws Exception {
        companyDetails = new CompanyDetails("luqEnt",
                new Address("1", "1", "1", "1", "1", "1", Country.MALAYSIA),
                "2222", Structure.SOLE);
        accountAdministratorDetails = new AccountAdministratorDetails("luq", "hakim", "***", "***");
        registration = new RegistrationApplication(UUID.randomUUID(), companyDetails, accountAdministratorDetails);
    }

    @Test
    public void resubmitUnderReview_shouldThrowException() {
        RuntimeException error = Assert.assertThrows(InvalidRegistrationException.class, () -> {
            registration.resubmit(companyDetails, accountAdministratorDetails);
        });
        System.out.println(error.getMessage());
    }

    @Test
    public void resubmitApproved_shouldThrowException() {
        registration.administer(UUID.randomUUID(), RegistrationStatus.APPROVED, "done");
        RuntimeException error = Assert.assertThrows(InvalidRegistrationException.class, () -> {
            registration.resubmit(companyDetails, accountAdministratorDetails);
        });
        System.out.println(error.getMessage());
    }

    @Test
    public void resubmit_currentStatusMustBeInProgress(){
        registration.administer(UUID.randomUUID(), RegistrationStatus.REJECTED, "");
        registration.resubmit(companyDetails, accountAdministratorDetails);
        Assert.assertTrue(registration.isUnderReview());
    }

    @Test
    public void resubmitOnce_detailsHistoryLengthMustBePlusOne(){
        int initialLength = registration.getDetailsHistory().size();
        registration.administer(UUID.randomUUID(), RegistrationStatus.REJECTED, "");
        registration.resubmit(companyDetails, accountAdministratorDetails);
        Assert.assertEquals(initialLength+1, registration.getDetailsHistory().size());
    }

    @Test
    public void administerRejected_shouldThrowException() {
        registration.administer(UUID.randomUUID(), RegistrationStatus.REJECTED, "");
        Assert.assertThrows(InvalidRegistrationException.class, () -> {
            registration.administer(UUID.randomUUID(), RegistrationStatus.REJECTED, "");
        });
    }

    @Test
    public void administerApproved_shouldThrowException() {
        registration.administer(UUID.randomUUID(), RegistrationStatus.APPROVED, "");
        Assert.assertThrows(InvalidRegistrationException.class, () -> {
            registration.administer(UUID.randomUUID(), RegistrationStatus.APPROVED, "");
        });
    }

    @Test
    public void administerOnce_administrationLengthMustBeOne(){
        int initialLength = registration.getAdministrationHistory().size();
        registration.administer(UUID.randomUUID(), RegistrationStatus.REJECTED, "");
        Assert.assertEquals(initialLength+1, registration.getAdministrationHistory().size());
    }

    @Test
    public void administerToApproved_statusBeforeMustBeUnderReview_andNewStatusMustBeApproved(){
        registration.administer(UUID.randomUUID(), RegistrationStatus.APPROVED, "");
        RegistrationAdministration administration = registration.currentAdministration();
        Assert.assertEquals(RegistrationStatus.UNDER_REVIEW, administration.statusBefore());
        Assert.assertEquals(RegistrationStatus.APPROVED, administration.newStatus());
    }

    @Test
    public void administerToRejected_currentStatusMustBeRejected(){
        registration.administer(UUID.randomUUID(), RegistrationStatus.REJECTED, "");
        Assert.assertTrue(registration.isRejected());
    }

    @Test
    public void administerToApproved_currentStatusMustBeApproved(){
        registration.administer(UUID.randomUUID(), RegistrationStatus.APPROVED, "");
        Assert.assertTrue(registration.isApproved());
    }

    @Test
    public void administerToUnderReview_shouldThrowException() {
        Assert.assertThrows(InvalidRegistrationException.class, () -> {
            registration.administer(UUID.randomUUID(), RegistrationStatus.UNDER_REVIEW, "");
        });
    }
}
