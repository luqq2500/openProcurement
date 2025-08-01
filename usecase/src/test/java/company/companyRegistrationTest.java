package company;

import address.CountryCode;
import company.api.CompanyRegistrationApplier;
import company.dto.ApplyCompanyRegistrationCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class companyRegistrationTest {
    private List<CompanyRegistration> registrations;
    private List<CompanyCountryRegistrationRule> rules;
    @Before
    public void setUp() throws Exception {
        registrations = List.of(
                new CompanyRegistration("Terraform", "202380061600", "202580091900",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CountryCode.MY, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration("PetaByte", "220389997777", "777766664444",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration("PetaByte", "220389997766", "777766664400",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration("PetaByte", "220380007777", "777766660044",
                        CompanyStructure.SOLE, CountryCode.MY, CompanyRegistrationStatus.REJECTED)
        );
        rules = List.of(
                new CompanyCountryRegistrationRule(
                        CountryCode.MY,
                        12,
                        "\\d{12}",
                        12,
                        "\\d{12}",
                        List.of(CompanyStructure.SOLE, CompanyStructure.PRIVATE_LIMITED_COMPANY))
        );
    }

    @Test
    public void applyDuplicateRegistrationNumberShouldThrowException() throws RuntimeException {
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Terrabyte",
                "202380061600",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY
        );
        CompanyRegistrationApplier applier = new ApplyCompanyRegistration(()->registrations, ()->rules);
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
                CountryCode.MY
        );
        CompanyRegistrationApplier applier = new ApplyCompanyRegistration(()->List.of(registrations.get(2)), ()->rules);
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
                CountryCode.MY
        );
        CompanyRegistrationApplier applier = new ApplyCompanyRegistration(()->registrations, ()->rules);
        applier.apply(command);
    }
}
