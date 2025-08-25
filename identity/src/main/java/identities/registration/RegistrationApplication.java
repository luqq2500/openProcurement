package identities.registration;

import java.time.LocalDateTime;
import java.util.*;

public class RegistrationApplication {
    private final UUID requestId;
    private RegistrationStatus currentStatus;
    private final List<RegistrationDetails> detailsHistory = new ArrayList<>();
    private final List<RegistrationAdministration> administrationHistory = new ArrayList<>();

    public RegistrationApplication(UUID requestId, CompanyDetails companyDetails, AccountAdministratorDetails accountAdministratorDetails) {
        this.requestId = requestId;
        this.detailsHistory.add(new RegistrationDetails(companyDetails, accountAdministratorDetails));
        this.currentStatus = RegistrationStatus.UNDER_REVIEW;
    }

    public void administer(UUID administratorId, RegistrationStatus newStatus, String notes) {
        if (!isUnderReview() || !currentStatus.checkStatusChangeTo(newStatus)) {
            throw new InvalidRegistrationException("Only under review registration can be administered.");
        }
        administrationHistory.add(new RegistrationAdministration(administratorId, currentStatus, newStatus, notes, LocalDateTime.now()));
        currentStatus = newStatus;
    }

    public void resubmit(CompanyDetails companyDetails, AccountAdministratorDetails accountAdministratorDetails) {
        if (!isRejected()){
            throw new InvalidRegistrationException("Only rejected registrations can be resubmitted.");
        }
        detailsHistory.add(new RegistrationDetails(companyDetails, accountAdministratorDetails));
        currentStatus = RegistrationStatus.UNDER_REVIEW;
    }

    public boolean isRejected(){
        return currentStatus == RegistrationStatus.REJECTED;
    }
    public boolean isApproved(){
        return currentStatus == RegistrationStatus.APPROVED;
    }
    public boolean isUnderReview(){
        return currentStatus == RegistrationStatus.UNDER_REVIEW;
    }
    public RegistrationDetails currentDetails() {
        return detailsHistory.getLast();
    }
    public RegistrationAdministration currentAdministration() {
        return administrationHistory.getLast();
    }
    public UUID getRequestId() {
        return requestId;
    }
    public String getBrn(){
        return this.currentDetails().companyDetails().brn();
    }
    public String getCompanyName(){
        return this.currentDetails().companyDetails().companyName();
    }
    public CompanyDetails getCompanyDetails() {
        return this.currentDetails().companyDetails();
    }
    public List<RegistrationDetails> getDetailsHistory() {return detailsHistory;}
    public List<RegistrationAdministration> getAdministrationHistory() {return administrationHistory;}

    public boolean validForResubmit(){
        return currentStatus == RegistrationStatus.REJECTED;
    }
}
