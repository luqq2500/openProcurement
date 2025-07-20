package administrator;

import address.AddressCommand;
import administrator.api.CompanyRegistrationApplicationAdjudicator;
import administrator.exception.InvalidAdjudicationCompanyRegistrationException;
import administrator.exception.InvalidAdministratorRoleRuleException;
import company.api.CompanyRegistrator;
import company.RegisterCompany;
import company.Company;
import company.CompanyRegistrationApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import administrator.api.AdministratorRoleResponsibilityValidator;
import administrator.exception.NotAuthorizedAdministratorRoleResponsibility;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdjudicateCompanyRegistrationApplicationIntegrationTest {
    private List<CompanyRegistrationApplication> applications;
    private CompanyRegistrationApplicationAdjudicator adjudicator;
    private Administrator authorizedAdministratorRole;
    private Administrator notAuthorizedAdministratorRole;
    private Administrator notRegisteredAdministratorRole;

    @Before
    public void setUp() {
        AddressCommand addressCommand = new AddressCommand(
                "1, Jalan Dahlia 8/2, Taman Dahlia",
                "Bandar Baru Salak Tinggi",
                null,
                "Petaling Jaya",
                "Selangor",
                "46100",
                "Malaysia"
        );
        applications = List.of(
                new CompanyRegistrationApplication("223hshvXc", "Cathedral", "202380061600", "202380061600", "Cooperative", addressCommand, LocalDateTime.now(), "Pending", null, null),
                new CompanyRegistrationApplication("2772bbbdhc", "ForgeNet", "202380061677", "202380061677", "Cooperative", addressCommand, LocalDateTime.now(), "Processing", "gsg1929mm", LocalDateTime.now()),
                new CompanyRegistrationApplication("hpwsb99aiq", "Continuum", "202380061699", "202380061699", "Cooperative", addressCommand, LocalDateTime.now(), "Approved", "gsg1929mm", LocalDateTime.now()),
                new CompanyRegistrationApplication("727mspbdg99", "Sleep Token", "202380061655", "202380061655", "Cooperative", addressCommand, LocalDateTime.now(), "Rejected", "gsg1929mm", LocalDateTime.now())
        );
        List<Administrator> administrators = List.of(
                new Administrator("hakimluqq25@gmail.com", "haha", "luq", "hakim", "Adjudicator"),
                new Administrator("hakimluqq77@gmail.com", "haha", "luq", "hakim", "Facilitator")
        );
        var roleResponsibilities = List.of(
                new AdministratorRoleResponsibilities("Adjudicator", Set.of("processCompanyRegistrationApplication", "approveCompanyRegistrationApplication", "rejectCompanyRegistrationApplication")),
                new AdministratorRoleResponsibilities("Facilitator", Set.of("facilitateOperation"))
        );
        List<Company> companies = new ArrayList<>();
        AdministratorRoleResponsibilityValidator validator = new ValidateAdministratorRoleResponsibility(()->roleResponsibilities);
        CompanyRegistrator registrator = new RegisterCompany(()->companies);
        adjudicator = new AdjudicateCompanyRegistrationApplication(()->applications, validator, registrator);
        authorizedAdministratorRole = administrators.stream().filter(administrator -> administrator.getRole().equals("Adjudicator")).findFirst().orElse(null);
        notAuthorizedAdministratorRole = administrators.stream().filter(administrator -> !administrator.getRole().equals("Adjudicator")).findFirst().orElse(null);
        notRegisteredAdministratorRole = new Administrator("sss", "ss11", "ww2", "ww2", "Finance");
    }

    @Test
    public void validAdministratorProcessApplicationWillReturnProcessingApplication() {
        CompanyRegistrationApplication pendingApplication = applications.stream()
                .filter(application -> application.status().equals("Pending"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.process(pendingApplication, authorizedAdministratorRole);
        Assert.assertNotNull(application);
        Assert.assertEquals("Processing", application.status());
        System.out.println(application);
    }

    @Test
    public void validAdministratorProcessNonPendingApplicationWillThrowException() {
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication approvedApplication = applications.stream()
                .filter(application -> application.status().equals("Approved"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication rejectedApplication = applications.stream()
                .filter(application -> application.status().equals("Rejected"))
                .findFirst()
                .orElse(null);
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.process(processingApplication, authorizedAdministratorRole);});
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.process(approvedApplication, authorizedAdministratorRole);});
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.process(rejectedApplication, authorizedAdministratorRole);});
    }

    @Test
    public void validAdministratorApproveProcessingApplicationWillReturnApprovedApplication() {
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.approve(processingApplication, authorizedAdministratorRole);
        Assert.assertNotNull(application);
        Assert.assertEquals("Approved", application.status());
    }

    @Test
    public void validAdministratorApproveNonProcessingApplicationWillThrowException() {
        CompanyRegistrationApplication pendingApplication = applications.stream()
                .filter(application -> application.status().equals("Pending"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication approvedApplication = applications.stream()
                .filter(application -> application.status().equals("Approved"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication rejectedApplication = applications.stream()
                .filter(application -> application.status().equals("Rejected"))
                .findFirst()
                .orElse(null);
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.approve(pendingApplication, authorizedAdministratorRole);});
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.approve(approvedApplication, authorizedAdministratorRole);});
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.approve(rejectedApplication, authorizedAdministratorRole);});
    }

    @Test
    public void validAdministratorRejectProcessingApplicationWillReturnRejectedApplication() {
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.reject(processingApplication, authorizedAdministratorRole);
        Assert.assertNotNull(application);
        Assert.assertEquals("Rejected", application.status());
    }

    @Test
    public void validAdministratorRejectNonProcessingApplicationWillThrowException() {
        CompanyRegistrationApplication pendingApplication = applications.stream()
                .filter(application -> application.status().equals("Pending"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication approvedApplication = applications.stream()
                .filter(application -> application.status().equals("Approved"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication rejectedApplication = applications.stream()
                .filter(application -> application.status().equals("Rejected"))
                .findFirst()
                .orElse(null);
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.reject(pendingApplication, authorizedAdministratorRole);});
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.reject(approvedApplication, authorizedAdministratorRole);});
        Assert.assertThrows(InvalidAdjudicationCompanyRegistrationException.class,() -> {adjudicator.reject(rejectedApplication, authorizedAdministratorRole);});
    }

    @Test
    public void notAuthorizedAdministratorAdjudicateApplicationWillThrowException(){
        CompanyRegistrationApplication pendingApplication = applications.stream()
                .filter(application -> application.status().equals("Pending"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        NotAuthorizedAdministratorRoleResponsibility processException = Assert.assertThrows(NotAuthorizedAdministratorRoleResponsibility.class,() -> {adjudicator.process(pendingApplication, notAuthorizedAdministratorRole);});
        NotAuthorizedAdministratorRoleResponsibility approveException = Assert.assertThrows(NotAuthorizedAdministratorRoleResponsibility.class,() -> {adjudicator.approve(processingApplication, notAuthorizedAdministratorRole);});
        NotAuthorizedAdministratorRoleResponsibility rejectException = Assert.assertThrows(NotAuthorizedAdministratorRoleResponsibility.class,() -> {adjudicator.reject(processingApplication, notAuthorizedAdministratorRole);});
        System.out.println(processException.getMessage());
        System.out.println(approveException.getMessage());
        System.out.println(rejectException.getMessage());
    }

    @Test
    public void setNotRegisteredAdministratorAdjudicateApplicationWillThrowException(){
        CompanyRegistrationApplication pendingApplication = applications.stream()
                .filter(application -> application.status().equals("Pending"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        InvalidAdministratorRoleRuleException processException = Assert.assertThrows(InvalidAdministratorRoleRuleException.class,() -> {adjudicator.process(pendingApplication, notRegisteredAdministratorRole);});
        InvalidAdministratorRoleRuleException approveException = Assert.assertThrows(InvalidAdministratorRoleRuleException.class,() -> {adjudicator.approve(processingApplication, notRegisteredAdministratorRole);});
        InvalidAdministratorRoleRuleException rejectException = Assert.assertThrows(InvalidAdministratorRoleRuleException.class,() -> {adjudicator.reject(processingApplication, notRegisteredAdministratorRole);});
        System.out.println(processException.getMessage());
        System.out.println(approveException.getMessage());
        System.out.println(rejectException.getMessage());
    }
}
