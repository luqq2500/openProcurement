package applicant;

import address.CountryCode;
import administrator.InMemoryCompanyRegistrationRepository;
import applicant.api.CompanyRegistrationApplier;
import applicant.dto.ApplyCompanyRegistrationCommand;
import company.CompanyCountryRegistrationRule;
import company.CompanyRegistration;
import company.CompanyRegistrationStatus;
import company.CompanyStructure;
import company.spi.CompanyRegistrationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class applyCompanyRegistrationTest {
    private List<CompanyRegistration> registrations;
    private List<CompanyCountryRegistrationRule> rules;
    private CompanyRegistrationApplier applier;
    @Before
    public void setUp() throws Exception {
        Applicant applicant = new Applicant("Luqman", "Hakim", "hakimluqq25@gmail.com", "0114305988");
        registrations = List.of(
                new CompanyRegistration("Terraform", "202380061600", "202580091900",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CountryCode.MY, applicant, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration("PetaByte", "220389997777", "777766664444",
                        CompanyStructure.SOLE, CountryCode.MY, applicant, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration("PetaByte", "220389997766", "777766664400",
                        CompanyStructure.SOLE, CountryCode.MY, applicant, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration("PetaByte", "220380007777", "777766660044",
                        CompanyStructure.SOLE, CountryCode.MY, applicant, CompanyRegistrationStatus.REJECTED)
        );
        rules = List.of(
                new CompanyCountryRegistrationRule(
                        CountryCode.MY, 12, "\\d{12}", 12, "\\d{12}", List.of(CompanyStructure.SOLE, CompanyStructure.PRIVATE_LIMITED_COMPANY))
        );
        CompanyRegistrationRepository repository = new InMemoryCompanyRegistrationRepository();
        repository.addListOf(registrations);
        applier = new ApplyCompanyRegistration(repository, ()->rules);
    }

    @Test
    public void applyInvalidEmail_shouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Terrabyte",
                "202380061600",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY,
                "Luqman","Hakim", "hakimgmail.com", "0104305988"
        );
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> {applier.apply(command);});
        System.out.println(error.getMessage());
    }

    @Test
    public void applyDuplicateRegistrationNumberShouldThrowException() throws RuntimeException {
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Terrabyte",
                "202380061600",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY,
                "Luqman","Hakim", "hakim@gmail.com", "0104305988"
        );
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> {applier.apply(command);});
        System.out.println(error.getMessage());
    }

    @Test
    public void applyDuplicateCompanyShouldThrowException() throws RuntimeException {
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Terrabyte",
                "220389997766",
                "777766664400",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY,
                "Luqman","Hakim", "hakim@gmail.com", "0104305988"
        );
        RuntimeException error = Assert.assertThrows(RuntimeException.class, () -> {applier.apply(command);});
        System.out.println(error.getMessage());
    }

    @Test
    public void applyValidRegistrationNumberAndTaxNumberOfRejectedApplicationShouldNotThrowException() throws RuntimeException {
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "PetaByte",
                "220380007777",
                "777766660044",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY,
                "Luqman","Hakim", "hakim@gmail.com", "0104305988"
        );
        applier.apply(command);
    }
}
