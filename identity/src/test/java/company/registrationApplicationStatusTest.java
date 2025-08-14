package company;

import company.exception.InvalidRegistrationApplicationStatus;
import org.junit.Assert;
import org.junit.Test;

public class registrationApplicationStatusTest {
    @Test
    public void pendingToProcessing_shouldNotThrowException() {
        RegistrationApplicationStatus status = RegistrationApplicationStatus.PENDING;
        status.validateStatusUpdateTo(RegistrationApplicationStatus.PROCESSING);
    }
    @Test
    public void processingToApproved_shouldNotThrowException() {
        RegistrationApplicationStatus status = RegistrationApplicationStatus.PROCESSING;
        status.validateStatusUpdateTo(RegistrationApplicationStatus.APPROVED);
    }
    @Test
    public void processingToRejected_shouldNotThrowException() {
        RegistrationApplicationStatus status = RegistrationApplicationStatus.PROCESSING;
        status.validateStatusUpdateTo(RegistrationApplicationStatus.REJECTED);
    }
    @Test
    public void pendingToInvalidStatus_shouldThrowException() {
        RegistrationApplicationStatus status = RegistrationApplicationStatus.PENDING;
        Assert.assertThrows(InvalidRegistrationApplicationStatus.class,
                () -> status.validateStatusUpdateTo(RegistrationApplicationStatus.PENDING));
        Assert.assertThrows(InvalidRegistrationApplicationStatus.class,
                () -> status.validateStatusUpdateTo(RegistrationApplicationStatus.APPROVED));
        Assert.assertThrows(InvalidRegistrationApplicationStatus.class,
                () -> status.validateStatusUpdateTo(RegistrationApplicationStatus.REJECTED));
    }
}
