package applicant;

import address.Address;
import address.Country;
import applicant.dto.ApplyCompanyRegistrationResponse;
import applicant.exception.CompanyRegistrationNumberNotApplicableForRegistration;
import applicant.exception.CompanyTaxNumberNotApplicableForRegistration;
import company.MockCompanyRegistrationRepository;
import applicant.api.CompanyRegistrationApplier;
import applicant.dto.ApplyCompanyRegistrationRequest;
import company.*;
import company.exception.CompanyRegistrationRequestExpired;
import company.exception.CompanyRegistrationRequestNotFound;
import company.spi.CompanyCountryRegistrationRuleRepository;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRegistrationRequestRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class applyCompanyRegistrationTest {
    private String mockEmail;
    private Address mockAddress;
    private CompanyRegistrationRequest validRequest;
    private CompanyRegistrationRequest expiredRequest;
    private CompanyRegistrationApplier applier;
    @Before
    public void setUp() {
        mockAddress = new Address("1", "1", "Sepang", "43900", "Selangor", Country.MALAYSIA);
        validRequest = new CompanyRegistrationRequest("hakimluqq25@gmail.com");
        expiredRequest = new CompanyRegistrationRequest("hakimluqq19@gmail.com");
        validRequest.enable(LocalDateTime.now(), LocalDateTime.now().plusDays(1));
        expiredRequest.enable(LocalDateTime.now(), LocalDateTime.now().plusDays(-1));
        List<CompanyRegistrationRequest> requests = List.of(
                validRequest, expiredRequest
        );
        List<CompanyRegistration> registrations = List.of(
                new CompanyRegistration(validRequest.getEmail(), "PetaByte", mockAddress, "000000111111", "000000111111",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration(validRequest.getEmail(), "TerraForm", mockAddress, "000000222222", "000000222222",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration(validRequest.getEmail(), "Citadel", mockAddress, "000000333333", "000000333333",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration(validRequest.getEmail(), "PetaByte", mockAddress, "000000444444", "000000444444",
                        CompanyStructure.SOLE, CompanyRegistrationStatus.REJECTED)
        );
        List<CompanyCountryRegistrationRule> rules = List.of(
                new CompanyCountryRegistrationRule(
                        Country.MALAYSIA, 12, "\\d{12}", 12, "\\d{12}", List.of(CompanyStructure.SOLE, CompanyStructure.PRIVATE_LIMITED_COMPANY))
        );
        CompanyRegistrationRequestRepository requestRepository = new MockCompanyRegistrationRequestRepository();
        CompanyRegistrationRepository repository = new MockCompanyRegistrationRepository();
        CompanyCountryRegistrationRuleRepository ruleRepository = new MockCompanyCountryRegistrationRuleRepository();
        rules.forEach(ruleRepository::add);
        registrations.forEach(repository::add);
        requests.forEach(requestRepository::add);
        applier = new ApplyCompanyRegistration(requestRepository, repository, ruleRepository);
    }

    @Test
    public void validApply_shouldNotThrowException() {
        ApplyCompanyRegistrationRequest command = new ApplyCompanyRegistrationRequest(
                validRequest.getId(),
                "Terrabyte",
                mockAddress,
                "200202028888",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY
        );
        ApplyCompanyRegistrationResponse response = applier.apply(command);
        System.out.println(response);
    }

    @Test
    public void requestIdDoesNotExist_shouldThrowException() {
        ApplyCompanyRegistrationRequest command = new ApplyCompanyRegistrationRequest(
                UUID.randomUUID(),
                "Terrabyte",
                mockAddress,
                "000000111111",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY
        );
        CompanyRegistrationRequestNotFound exception = Assert.assertThrows(CompanyRegistrationRequestNotFound.class, () -> applier.apply(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void appliedRegistrationNumber_shouldThrowException() {
        ApplyCompanyRegistrationRequest command = new ApplyCompanyRegistrationRequest(
                validRequest.getId(),
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
                validRequest.getId(),
                "Terrabyte",
                mockAddress,
                "202380061600",
                "000000111111",
                CompanyStructure.PRIVATE_LIMITED_COMPANY
        );
        RuntimeException exception = Assert.assertThrows(CompanyTaxNumberNotApplicableForRegistration.class, () -> applier.apply(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void expiredRequest_shouldThrowException(){
        ApplyCompanyRegistrationRequest command = new ApplyCompanyRegistrationRequest(
                expiredRequest.getId(),
                "Terrabyte",
                mockAddress,
                "202380061600",
                "202380061600",
                CompanyStructure.PRIVATE_LIMITED_COMPANY
        );
        RuntimeException exception = Assert.assertThrows(CompanyRegistrationRequestExpired.class, () -> applier.apply(command));
        System.out.println(exception.getMessage());
    }
}