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
import java.util.Optional;

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
                "20223344", Structure.SOLE, "2223", "luqman", "hakim", null, "3535"));
    }

    @Test
    public void expiredRegistrationRequest_shouldThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(-7));
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "20223344", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        InvalidRegistrationApplication error = Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(applyRegistrationRequest));
        System.out.println(error.getMessage());
    }

    @Test
    public void appliedBrnApproved_shouldThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        RegistrationApplication registrationApplication = new RegistrationApplication(
                "TerraForm", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        registrationApplication.elevateStatusTo(RegistrationApplicationStatus.PROCESSING);
        registrationApplication.elevateStatusTo(RegistrationApplicationStatus.APPROVED);
        applicationRepository.save(registrationApplication);
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "20223344", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);

        Assert.assertThrows(InvalidRegistrationApplication.class, ()-> applier.apply(applyRegistrationRequest));
    }

    @Test
    public void appliedBrnRejected_shouldNotThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        RegistrationApplication registrationApplication = new RegistrationApplication(
                "TerraForm", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        registrationApplication.elevateStatusTo(RegistrationApplicationStatus.PROCESSING);
        registrationApplication.elevateStatusTo(RegistrationApplicationStatus.REJECTED);
        applicationRepository.save(registrationApplication);
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "20223344", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        applier.apply(applyRegistrationRequest);
    }

    @Test
    public void appliedBrnAndUnderReview_shouldThrowException() {
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        RegistrationApplication registrationApplication = new RegistrationApplication(
                "TerraForm", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        applicationRepository.save(registrationApplication);
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "3883", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        InvalidRegistrationApplication error = Assert.assertThrows(InvalidRegistrationApplication.class,
                ()-> applier.apply(applyRegistrationRequest));
        System.out.println(error.getMessage());
    }

    @Test
    public void duplicateBrnAndCompanyAccountIsActive_shouldThrowException() {
        Company company = new Company("Terraform", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", "2223", Structure.SOLE);
        companyRepository.save(company);
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "3883", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        InvalidRegistrationApplication error = Assert.assertThrows(InvalidRegistrationApplication.class,
                ()-> applier.apply(applyRegistrationRequest));
        System.out.println(error.getMessage());
    }

    @Test
    public void duplicateBrnAndCompanyAccountIsInactive_shouldNotThrowException() {
        Company company = new Company("Terraform", new Address("1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA),
                "3883", "2223", Structure.SOLE);
        company.terminateAccount();
        companyRepository.save(company);
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "3883", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        applier.apply(applyRegistrationRequest);
    }

    @Test
    public void nullUsername_usernameShouldEqualEmail(){
        RegistrationRequest registrationRequest = new RegistrationRequest("hakimluqq25", LocalDateTime.now().plusDays(7));
        requestRepository.save(registrationRequest);
        ApplyRegistrationRequest applyRegistrationRequest = new ApplyRegistrationRequest(registrationRequest.getId(), "Terraform",
                "1", "1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA,
                "3883", Structure.SOLE, "2223", "luqman", "hakim", null, "3535");
        RegistrationApplier applier = new ApplyRegistration(applicationRepository, requestRepository, companyRepository);
        applier.apply(applyRegistrationRequest);

        Optional<RegistrationApplication> applicationOptional =  applicationRepository.getByBrn(applyRegistrationRequest.brn());
        applicationOptional.ifPresent(registrationApplication -> Assert.assertEquals(registrationApplication.getUsername(), registrationRequest.getEmail()));
    }
}
