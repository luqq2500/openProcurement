package company;

import address.Address;
import address.Country;
import administrator.Administrator;
import administrator.AdministratorRoles;
import company.exception.InvalidCompanyRegistrationStatus;
import org.junit.Assert;
import org.junit.Test;

public class companyRegistrationTest {
    @Test
    public void pendingToProcessing_shouldNotThrowException() {
        Administrator administrator = new Administrator("Luqman", "Hakim", "hakimluqq25@gmail.com", "123", AdministratorRoles.PROCUREMENT_ADMINISTRATOR);
        CompanyRegistration registration = new CompanyRegistration(
                "x", "x",
                new Address("1", "1", "1", "sepang", "43900", "selangor", Country.MALAYSIA),
                "2", "2", CompanyStructure.PUBLIC_LIMITED_COMPANY,
                CompanyRegistrationStatus.PENDING);
        CompanyRegistration updatedRegistration = registration.elevateStatusToProcessing(administrator);
    }

    @Test
    public void pendingToInvalidStatusUpdate_shouldThrowException() {
        Administrator administrator = new Administrator("Luqman", "Hakim", "hakimluqq25@gmail.com", "123", AdministratorRoles.PROCUREMENT_ADMINISTRATOR);
        CompanyRegistration registration = new CompanyRegistration(
                "x", "x",
                new Address("1", "1", "1", "sepang", "43900", "selangor", Country.MALAYSIA),
                "2", "2", CompanyStructure.PUBLIC_LIMITED_COMPANY,
                CompanyRegistrationStatus.PENDING);
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToApproved(administrator));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToRejected(administrator));
    }

    @Test
    public void processingToInvalidStatusUpdate_shouldThrowException() {
        Administrator administrator = new Administrator("Luqman", "Hakim", "hakimluqq25@gmail.com", "123", AdministratorRoles.PROCUREMENT_ADMINISTRATOR);
        CompanyRegistration registration = new CompanyRegistration(
                "x", "x",
                new Address("1", "1", "1", "sepang", "43900", "selangor", Country.MALAYSIA),
                "2", "2", CompanyStructure.PUBLIC_LIMITED_COMPANY,
                CompanyRegistrationStatus.PROCESSING);
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToProcessing(administrator));
    }

    @Test
    public void approvedToInvalidStatusUpdate_shouldThrowException() {
        Administrator administrator = new Administrator("Luqman", "Hakim", "hakimluqq25@gmail.com", "123", AdministratorRoles.PROCUREMENT_ADMINISTRATOR);
        CompanyRegistration registration = new CompanyRegistration(
                "x", "x",
                new Address("1", "1", "1", "sepang", "43900", "selangor", Country.MALAYSIA),
                "2", "2", CompanyStructure.PUBLIC_LIMITED_COMPANY,
                CompanyRegistrationStatus.APPROVED);
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToProcessing(administrator));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToApproved(administrator));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToRejected(administrator));
    }

    @Test
    public void rejectedToInvalidStatusUpdate_shouldThrowException() {
        Administrator administrator = new Administrator("Luqman", "Hakim", "hakimluqq25@gmail.com", "123", AdministratorRoles.PROCUREMENT_ADMINISTRATOR);
        CompanyRegistration registration = new CompanyRegistration(
                "x", "x",
                new Address("1", "1", "1", "sepang", "43900", "selangor", Country.MALAYSIA),
                "2", "2", CompanyStructure.PUBLIC_LIMITED_COMPANY,
                CompanyRegistrationStatus.REJECTED);
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToProcessing(administrator));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToApproved(administrator));
        Assert.assertThrows(InvalidCompanyRegistrationStatus.class, ()-> registration.elevateStatusToRejected(administrator));
    }


}
