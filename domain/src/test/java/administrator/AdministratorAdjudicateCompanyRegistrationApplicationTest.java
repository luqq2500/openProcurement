package administrator;

import administrator.api.AdministratorRoleResponsibilityValidator;
import administrator.api.CompanyRegistrationApplicationAdjudicator;
import administrator.application.AdjudicateCompanyRegistrationApplication;
import administrator.application.ValidateAdministratorRoleResponsibility;
import administrator.exception.NotAuthorizedAdministratorRoleResponsibility;
import administrator.model.Administrator;
import administrator.model.AdministratorRoleResponsibilities;
import company.api.CompanyRegistrator;
import company.application.RegisterCompany;
import company.exception.CompanyRegistrationApplicationAdjudicationException;
import company.model.Company;
import company.model.CompanyRegistrationApplication;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AdministratorAdjudicateCompanyRegistrationApplicationTest {
    private List<CompanyRegistrationApplication> applications;
    private CompanyRegistrationApplicationAdjudicator adjudicator;
    private Administrator authorizedAdministrator;
    private Administrator notAuthorizedAdministrator;

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
        var roleResponsibilities = List.of(
                new AdministratorRoleResponsibilities("Adjudicator", Set.of("processCompanyRegistrationApplication", "approveCompanyRegistrationApplication", "rejectCompanyRegistrationApplication"))
        );
        List<Company> companies = new ArrayList<>();
        AdministratorRoleResponsibilityValidator validator = new ValidateAdministratorRoleResponsibility(()->roleResponsibilities);
        CompanyRegistrator registrator = new RegisterCompany(()->companies);
        adjudicator = new AdjudicateCompanyRegistrationApplication(()->applications, validator, registrator);
        authorizedAdministrator = administrators.stream().filter(administrator -> administrator.getRole().equals("Adjudicator")).findFirst().orElse(null);
        notAuthorizedAdministrator = administrators.stream().filter(administrator -> !administrator.getRole().equals("Adjudicator")).findFirst().orElse(null);
    }
    @Test
    public void validAdministratorProcessApplicationWillReturnProcessingApplication() {
        CompanyRegistrationApplication pendingApplication = applications.stream()
                .filter(application -> application.status().equals("Pending"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.process(pendingApplication, authorizedAdministrator);
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
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.process(processingApplication, authorizedAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.process(approvedApplication, authorizedAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.process(rejectedApplication, authorizedAdministrator);});
    }

    @Test
    public void validAdministratorApproveProcessingApplicationWillReturnApprovedApplication() {
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.approve(processingApplication, authorizedAdministrator);
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
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.approve(pendingApplication, authorizedAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.approve(approvedApplication, authorizedAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.approve(rejectedApplication, authorizedAdministrator);});
    }

    @Test
    public void validAdministratorRejectProcessingApplicationWillReturnRejectedApplication() {
        CompanyRegistrationApplication processingApplication = applications.stream()
                .filter(application -> application.status().equals("Processing"))
                .findFirst()
                .orElse(null);
        CompanyRegistrationApplication application = adjudicator.reject(processingApplication, authorizedAdministrator);
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
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.reject(pendingApplication, authorizedAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.reject(approvedApplication, authorizedAdministrator);});
        Assert.assertThrows(CompanyRegistrationApplicationAdjudicationException.class,() -> {adjudicator.reject(rejectedApplication, authorizedAdministrator);});
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
        Assert.assertThrows(NotAuthorizedAdministratorRoleResponsibility.class,() -> {adjudicator.process(pendingApplication, notAuthorizedAdministrator);});
        Assert.assertThrows(NotAuthorizedAdministratorRoleResponsibility.class,() -> {adjudicator.approve(processingApplication, notAuthorizedAdministrator);});
        Assert.assertThrows(NotAuthorizedAdministratorRoleResponsibility.class,() -> {adjudicator.reject(processingApplication, notAuthorizedAdministrator);});
    }
}
