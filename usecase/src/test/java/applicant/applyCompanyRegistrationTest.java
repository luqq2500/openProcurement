package applicant;

import address.CountryCode;
import company.MockCompanyRegistrationRepository;
import applicant.api.CompanyRegistrationApplier;
import applicant.dto.ApplyCompanyRegistrationCommand;
import company.*;
import company.spi.CompanyCountryRegistrationRuleRepository;
import company.spi.CompanyRegistrationRepository;
import company.spi.CompanyRegistrationRequestRepository;
import notification.MockNotificationService;
import notification.NotificationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class applyCompanyRegistrationTest {
    private List<CompanyRegistrationRequest> requests;
    private List<CompanyRegistration> registrations;
    private List<CompanyCountryRegistrationRule> rules;
    private CompanyRegistrationApplier applier;
    @Before
    public void setUp() {
        Applicant mockApplicant = new Applicant("Luqman", "Hakim", "hakimluqq25@gmail.com", "0114305988");
        requests = List.of(
                new CompanyRegistrationRequest(mockApplicant)
        );
        registrations = List.of(
                new CompanyRegistration("Terraform", "000000000001", "000000000001",
                        CompanyStructure.PUBLIC_LIMITED_COMPANY, CountryCode.MY, mockApplicant, CompanyRegistrationStatus.PENDING),
                new CompanyRegistration("PetaByte", "000000000002", "000000000003",
                        CompanyStructure.SOLE, CountryCode.MY, mockApplicant, CompanyRegistrationStatus.PROCESSING),
                new CompanyRegistration("PetaByte", "000000000003", "000000000003",
                        CompanyStructure.SOLE, CountryCode.MY, mockApplicant, CompanyRegistrationStatus.APPROVED),
                new CompanyRegistration("PetaByte", "000000000004", "000000000004",
                        CompanyStructure.SOLE, CountryCode.MY, mockApplicant, CompanyRegistrationStatus.REJECTED)
        );
        rules = List.of(
                new CompanyCountryRegistrationRule(
                        CountryCode.MY, 12, "\\d{12}", 12, "\\d{12}", List.of(CompanyStructure.SOLE, CompanyStructure.PRIVATE_LIMITED_COMPANY))
        );
        CompanyRegistrationRepository repository = new MockCompanyRegistrationRepository();
        CompanyCountryRegistrationRuleRepository ruleRepository = new MockCompanyCountryRegistrationRuleRepository();
        CompanyRegistrationRequestRepository requestRepository = new MockCompanyRegistrationRequestRepository();
        NotificationService notificationService = new MockNotificationService();
        requests.forEach(requestRepository::add);
        rules.forEach(ruleRepository::add);
        registrations.forEach(repository::add);
        applier = new ApplyCompanyRegistration(repository, ruleRepository, requestRepository,notificationService);
    }

    @Test
    public void notExistRequestId_shouldThrowException() {
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Terrabyte",
                "202380061600",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY,
                "not_exist_request_id"
        );
        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> applier.apply(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void existRequestId_shouldNotThrowException() {
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Terrabyte",
                "202380061600",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY,
                requests.getFirst().getRequestId()
        );
        applier.apply(command);
    }

    @Test
    public void appliedRegistrationNumber_shouldThrowException() {
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Terrabyte",
                "000000000001",
                "200202028888",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY,
                requests.getFirst().getRequestId()
        );
        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> applier.apply(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void appliedTaxNumber_shouldThrowException() {
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Terrabyte",
                "202380061600",
                "000000000001",
                CompanyStructure.PRIVATE_LIMITED_COMPANY,
                CountryCode.MY,
                requests.getFirst().getRequestId()
        );
        RuntimeException exception = Assert.assertThrows(RuntimeException.class, () -> applier.apply(command));
        System.out.println(exception.getMessage());
    }
}