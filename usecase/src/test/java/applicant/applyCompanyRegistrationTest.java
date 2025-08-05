package applicant;

import address.Address;
import address.Country;
import applicant.exception.CompanyRegistrationNumberNotApplicableForRegistration;
import applicant.exception.CompanyTaxNumberNotApplicableForRegistration;
import company.MockCompanyRegistrationRepository;
import applicant.api.CompanyRegistrationApplier;
import applicant.dto.ApplyCompanyRegistrationRequest;
import company.*;
import company.spi.CompanyCountryRegistrationRuleRepository;
import company.spi.CompanyRegistrationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class applyCompanyRegistrationTest {
    private String mockEmail;
    private Address mockAddress;
    private CompanyRegistrationApplier applier;
    @Before
    public void setUp() {
        mockEmail = "hakimluqq25@gmail.com";
        mockAddress = new Address("1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA);
        List<CompanyRegistration> registrations = List.of(
                new CompanyRegistration(mockEmail, "PetaByte", mockAddress, "000000111111", "000000111111",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration(mockEmail, "TerraForm", mockAddress, "000000222222", "000000222222",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration(mockEmail, "Citadel", mockAddress, "000000333333", "000000333333",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration(mockEmail, "PetaByte", mockAddress, "000000444444", "000000444444",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.REJECTED)
        );
        List<CompanyCountryRegistrationRule> rules = List.of(
                new CompanyCountryRegistrationRule(
                        Country.MALAYSIA, 12, "\\d{12}", 12, "\\d{12}", List.of(CompanyStructure.SOLE, CompanyStructure.PRIVATE_LIMITED_COMPANY))
        );
        CompanyRegistrationRepository repository = new MockCompanyRegistrationRepository();
        CompanyCountryRegistrationRuleRepository ruleRepository = new MockCompanyCountryRegistrationRuleRepository();
        rules.forEach(ruleRepository::add);
        registrations.forEach(repository::add);
        applier = new ApplyCompanyRegistration(repository, ruleRepository);
    }

    @Test
    public void appliedRegistrationNumber_shouldThrowException() {
        ApplyCompanyRegistrationRequest command = new ApplyCompanyRegistrationRequest(
                mockEmail,
                "Terrabyte",
                mockAddress,
                "000000111111",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY
        );
        RuntimeException exception = Assert.assertThrows(CompanyRegistrationNumberNotApplicableForRegistration.class, () -> applier.apply(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void appliedTaxNumber_shouldThrowException() {
        ApplyCompanyRegistrationRequest command = new ApplyCompanyRegistrationRequest(
                mockEmail,
                "Terrabyte",
                mockAddress,
                "202380061600",
                "000000111111",
                CompanyStructure.PRIVATE_LIMITED_COMPANY
        );
        RuntimeException exception = Assert.assertThrows(CompanyTaxNumberNotApplicableForRegistration.class, () -> applier.apply(command));
        System.out.println(exception.getMessage());
    }
}