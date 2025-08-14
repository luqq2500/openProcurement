package company;

import company.address.Address;
import company.address.Country;
import company.api.RegistrationApplier;
import company.exception.InvalidRegistrationApplication;
import company.spi.CompanyRepository;
import company.spi.RegistrationApplicationRepository;
import company.spi.RegistrationRequestRepository;
import company.usecase.ApplyRegistration;
import company.usecase.ApplyRegistrationRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class applyRegistrationApplicationTest {
    private RegistrationRequestRepository requestRepository;
    private RegistrationApplicationRepository applicationRepository;
    private CompanyRepository companyRepository;
    @Before
    public void setUp() throws Exception {
        applicationRepository = new MockRegistrationApplicationRepository();
        requestRepository = new MockRegistrationRequestRepository();
        companyRepository = new MockCompanyRepository();
    }

    @Test
    public void validRegistrationRequest_shouldNotThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        requestRepository.save(registrationRequest);
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        applier.apply(new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "20223344", Structure.SOLE, "2223"));
    }

    @Test
    public void expiredRegistrationRequest_shouldThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(-7));
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "20223344", Structure.SOLE, "2223");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        InvalidRegistrationApplication error = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(applyRegistrationRequest));
        System.out.println(error.getMessage());
    }

    @Test
    public void appliedBrnAndNotUnderReview_shouldNotThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        RegistrationApplication registrationApplication = new RegistrationApplication(
                "TerraForm", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", Structure.SOLE, "2223");
        registrationApplication.elevateStatusTo(RegistrationApplicationStatus.PROCESSING);
        registrationApplication.elevateStatusTo(RegistrationApplicationStatus.REJECTED);
        applicationRepository.save(registrationApplication);
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "20223344", Structure.SOLE, "2223");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        applier.apply(applyRegistrationRequest);
    }

    @Test
    public void appliedBrnAndUnderReview_shouldThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        RegistrationApplication registrationApplication = new RegistrationApplication(
                "TerraForm", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", Structure.SOLE, "2223");
        applicationRepository.save(registrationApplication);
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "3883", Structure.SOLE, "2223");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        InvalidRegistrationApplication error = Assert.assertThrows(InvalidRegistrationApplication.class,
                ()-> applier.apply(applyRegistrationRequest));
        System.out.println(error.getMessage());
    }

    @Test
    public void duplicateBrnAndCompanyAccountIsActive_shouldThrowException() {
        CompanyAccount companyAccount = new CompanyAccount("Terraform", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", "2223", Structure.SOLE);
        companyRepository.save(companyAccount);
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "3883", Structure.SOLE, "2223");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        InvalidRegistrationApplication error = Assert.assertThrows(InvalidRegistrationApplication.class,
                ()-> applier.apply(applyRegistrationRequest));
        System.out.println(error.getMessage());
    }

    @Test
    public void duplicateBrnAndCompanyAccountIsInactive_shouldNotThrowException() {
        CompanyAccount companyAccount = new CompanyAccount("Terraform", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", "2223", Structure.SOLE);
        companyAccount.terminateAccount();
        companyRepository.save(companyAccount);
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "3883", Structure.SOLE, "2223");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        applier.apply(applyRegistrationRequest);
    }
}
