package domain.registration;

import domain.administrator.Administrator;
import domain.administrator.AdministratorRole;
import domain.registration.exception.InvalidRegistrationApplication;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationApplication(
        UUID registrationId,
        CompanyDetails companyDetails,
        AccountAdministratorDetails accountAdministratorDetails,
        RegistrationApplicationStatus status,
        LocalDateTime applicationDate,
        UUID requestId,
        UUID administratorId
) {
    public RegistrationApplication changeDetails(CompanyDetails companyDetails, AccountAdministratorDetails accountAdministratorDetails){
        if (!status.checkStatusChangeTo(RegistrationApplicationStatus.UNDER_REVIEW)){
            throw new InvalidRegistrationApplication("Only rejected registration can change details.");
        };
        return new RegistrationApplication(UUID.randomUUID(),
                companyDetails, accountAdministratorDetails,
                RegistrationApplicationStatus.UNDER_REVIEW, LocalDateTime.now(), requestId , null);
    }
    public RegistrationApplication updateStatus(Administrator administrator, RegistrationApplicationStatus newStatus){
        if (!administrator.validateRole(AdministratorRole.IDENTITY_ADMINISTRATOR)){
            throw new InvalidRegistrationApplication("Only " + AdministratorRole.IDENTITY_ADMINISTRATOR + " can update status.");
        }
        if (!status.checkStatusChangeTo(newStatus)){
            throw new InvalidRegistrationApplication("Status change invalid.");
        };
        return new RegistrationApplication(UUID.randomUUID(),
                companyDetails, accountAdministratorDetails,
                newStatus, LocalDateTime.now(),requestId,administrator.getAdministratorId());
    }
    public boolean isApproved(){
        return status.equals(RegistrationApplicationStatus.APPROVED);
    }
    public boolean isRejected(){
        return status.equals(RegistrationApplicationStatus.REJECTED);
    }
    public boolean isUnderReview(){
        return status.equals(RegistrationApplicationStatus.UNDER_REVIEW);
    }
    public String getBrn(){
        return companyDetails().brn();
    }
    public String getCompanyName(){
        return companyDetails().companyName();
    }
    public LocalDateTime getApplicationDate(){
        return applicationDate;
    }
}
