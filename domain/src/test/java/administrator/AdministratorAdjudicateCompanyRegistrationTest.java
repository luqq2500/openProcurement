package administrator;

import administrator.api.CompanyRegistrationApplicationAdjudicator;
import administrator.application.AdministratorAdjudicateCompanyRegistrationApplication;
import administrator.exception.InvalidAdministratorRole;
import administrator.model.Administrator;
import company.exception.CompanyRegistrationApplicationAdjudicationException;
import company.model.CompanyRegistrationApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class AdministratorAdjudicateCompanyRegistrationTest {
    private List<CompanyRegistrationApplication> applications;
    private Administrator validAdministrator;
    private Administrator invalidAdministrator;
    private CompanyRegistrationApplicationAdjudicator adjudicator;

    @Before
    public void setUp() {
        applications = List.of(
                new CompanyRegistrationApplication("223hshvXc", "Cathedral", "202380061600", "202380061600", "Cooperative", "MY", LocalDateTime.now(), "Pending", null, null),
                new CompanyRegistrationApplication("2772bbbdhc", "ForgeNet", "202380061677", "202380061677", "Cooperative", "MY", LocalDateTime.now(), "Processing", "gsg1929mm", LocalDateTime.now()),
                new CompanyRegistrationApplication("hpwsb99aiq", "Continuum", "202380061699", "202380061699", "Cooperative", "MY", LocalDateTime.now(), "Approved", "gsg1929mm", LocalDateTime.now()),
                new CompanyRegistrationApplication("727mspbdg99", "Sleep Token", "202380061655", "202380061655", "Cooperative", "MY", LocalDateTime.now(), "Rejected", "gsg1929mm", LocalDateTime.now())
        );
        List<Administrator> administrators = List.of(
                new Administrator("hakimluqq25@gmail.com", "haha", "luq", "hakim", "Adjudicator"),
                new Administrator("hakimluqq77@gmail.com", "haha", "luq", "hakim", "Facilitator")
        );
        adjudicator = new AdministratorAdjudicateCompanyRegistrationApplication(()->applications);
        validAdministrator = administrators.stream().filter(administrator -> administrator.getRole().equals("Adjudicator")).findFirst().orElse(null);
        invalidAdministrator = administrators.stream().filter(administrator -> !administrator.getRole().equals("Adjudicator")).findFirst().orElse(null);
    }
    @Test
    public void validAdministratorProcessApplicationWillReturnProcessingApplication() {
        CompanyRegistrationApplication pendingApplication = applications.stream()
                .filter(application -> application.status().equals("Pending"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.process(pendingApplication, validAdministrator);
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
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.process(processingApplication, validAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.process(approvedApplication, validAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.process(rejectedApplication, validAdministrator);});
    }

    @Test
    public void validAdministratorApproveProcessingApplicationWillReturnApprovedApplication() {
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.approve(processingApplication, validAdministrator);
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
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.approve(pendingApplication, validAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.approve(approvedApplication, validAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.approve(rejectedApplication, validAdministrator);});
    }

    @Test
    public void validAdministratorRejectProcessingApplicationWillReturnRejectedApplication() {
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.reject(processingApplication, validAdministrator);
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
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.reject(pendingApplication, validAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.reject(approvedApplication, validAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.reject(rejectedApplication, validAdministrator);});
    }

    @Test
    public void invalidAdministratorAdjudicateApplicationWillThrowException(){
        CompanyRegistrationApplication pendingApplication = applications.stream()
                .filter(application -> application.status().equals("Pending"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        Assert.assertThrows(InvalidAdministratorRole.class,() -> {adjudicator.process(pendingApplication, invalidAdministrator);});
        Assert.assertThrows(InvalidAdministratorRole.class,() -> {adjudicator.approve(processingApplication, invalidAdministrator);});
        Assert.assertThrows(InvalidAdministratorRole.class,() -> {adjudicator.reject(processingApplication, invalidAdministrator);});
    }
}
