package company;

import company.exception.InvalidCompanyRegistrationStatus;
import org.junit.Assert;
import org.junit.Test;

public class companyRegistrationStatusTest {
    @Test
    public void pendingToValidStatus_shouldNotThrowException() {
        CompanyRegistrationStatus status = CompanyRegistrationStatus.PENDING;
        status.checkChangeStatusTo(CompanyRegistrationStatus.PROCESSING);
    }
    @Test
    public void processingToValidStatus_shouldNotThrowException() {
        CompanyRegistrationStatus status = CompanyRegistrationStatus.PROCESSING;
        status.checkChangeStatusTo(CompanyRegistrationStatus.APPROVED);
        status.checkChangeStatusTo(CompanyRegistrationStatus.REJECTED);
    }
    @Test
    public void pendingToInvalidStatus_shouldThrowException() {
        CompanyRegistrationStatus status = CompanyRegistrationStatus.PENDING;
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.PENDING));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.APPROVED));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.REJECTED));
    }
    @Test
    public void processingToInvalidStatus_shouldThrowException() {
        CompanyRegistrationStatus status = CompanyRegistrationStatus.PROCESSING;
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.PENDING));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.PROCESSING));
    }
    @Test
    public void updateApprovedStatus_shouldThrowException() {
        CompanyRegistrationStatus status = CompanyRegistrationStatus.APPROVED;
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.PENDING));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.PROCESSING));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.APPROVED));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.REJECTED));
    }
    @Test
    public void updateRejectedStatus_shouldThrowException() {
        CompanyRegistrationStatus status = CompanyRegistrationStatus.REJECTED;
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.PENDING));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.PROCESSING));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.APPROVED));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, () -> status.checkChangeStatusTo(CompanyRegistrationStatus.REJECTED));
    }
}
