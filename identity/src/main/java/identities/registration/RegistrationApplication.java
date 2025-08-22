package identities.registration;

import identities.registration.exception.InvalidRegistrationApplication;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationApplication(
        UUID requestId,
        CompanyDetails companyDetails,
        AccountAdministratorDetails accountAdministratorDetails,
        RegistrationStatus status,
        LocalDateTime applicationDate,
        int version,
        UUID administeredByAdministratorId
) {
    public RegistrationApplication changeDetails(CompanyDetails companyDetails, AccountAdministratorDetails accountAdministratorDetails){
        if (!status.checkStatusChangeTo(RegistrationStatus.UNDER_REVIEW)){
            throw new InvalidRegistrationApplication("Only rejected registration can change details.");
        };
        return new RegistrationApplication(UUID.randomUUID(),
                companyDetails, accountAdministratorDetails,
                RegistrationStatus.UNDER_REVIEW, LocalDateTime.now(), version+1, null);
    }
    public RegistrationApplication updateStatus(UUID administratorId, RegistrationStatus newStatus){
        if (!status.checkStatusChangeTo(newStatus)){
            throw new InvalidRegistrationApplication("Status change invalid.");};
        return new RegistrationApplication(UUID.randomUUID(),
                companyDetails, accountAdministratorDetails,
                newStatus, applicationDate,version,administratorId);
    }
    public boolean isApproved(){
        return status.equals(RegistrationStatus.APPROVED);
    }
    public boolean isRejected(){
        return status.equals(RegistrationStatus.REJECTED);
    }
    public boolean isUnderReview(){
        return status.equals(RegistrationStatus.UNDER_REVIEW);
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
