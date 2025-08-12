package company;

import org.junit.Assert;
import org.junit.Test;

public class registrationApplicationStatusTest {
    @Test
    public void pendingToProcessing_shouldNotThrowException() {
        CompanyRegistrationApplicationStatus status = CompanyRegistrationApplicationStatus.PENDING;
        status.validateStatusUpdateTo(CompanyRegistrationApplicationStatus.PROCESSING);
    }
    @Test
    public void processingToApproved_shouldNotThrowException() {
        CompanyRegistrationApplicationStatus status = CompanyRegistrationApplicationStatus.PROCESSING;
        status.validateStatusUpdateTo(CompanyRegistrationApplicationStatus.APPROVED);
    }
    @Test
    public void processingToRejected_shouldNotThrowException() {
        CompanyRegistrationApplicationStatus status = CompanyRegistrationApplicationStatus.PROCESSING;
        status.validateStatusUpdateTo(CompanyRegistrationApplicationStatus.REJECTED);
    }
    @Test
    public void pendingToInvalidStatus_shouldThrowException() {
        CompanyRegistrationApplicationStatus status = CompanyRegistrationApplicationStatus.PENDING;
        Assert.assertThrows(InvalidCompanyRegistrationApplicationStatus.class,
                () -> status.validateStatusUpdateTo(CompanyRegistrationApplicationStatus.PENDING));
        Assert.assertThrows(InvalidCompanyRegistrationApplicationStatus.class,
                () -> status.validateStatusUpdateTo(CompanyRegistrationApplicationStatus.APPROVED));
        Assert.assertThrows(InvalidCompanyRegistrationApplicationStatus.class,
                () -> status.validateStatusUpdateTo(CompanyRegistrationApplicationStatus.REJECTED));
    }
}
