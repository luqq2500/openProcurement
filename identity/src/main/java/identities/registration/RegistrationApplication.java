package identities.registration;

import java.time.LocalDateTime;
import java.util.*;

public record RegistrationApplication(
        UUID requestId,
        UUID applicationId,
        CompanyDetails companyDetails,
        AccountAdminDetails accountAdminDetails,
        LocalDateTime appliedOn
){
    public RegistrationApplication resubmit(CompanyDetails companyDetails, AccountAdminDetails accountAdminDetails) {
        return new RegistrationApplication(requestId, UUID.randomUUID(),
                companyDetails, accountAdminDetails, LocalDateTime.now());
    }
    public String getCompanyName() {
        return companyDetails.companyName();
    }
    public String getBrn(){
        return companyDetails.brn();
    }
    public UUID getRequestId() {
        return requestId;
    }
    public UUID getApplicationId() {
        return applicationId;
    }
    public String getEmail(){
        return accountAdminDetails.email();
    }
}
