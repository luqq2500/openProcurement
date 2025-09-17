package domain.registration;

import java.time.LocalDateTime;
import java.util.*;

public record Registration(
        UUID registrationId,
        UUID accountId,
        CompanyDetails companyDetails,
        AccountAdminDetails accountAdminDetails,
        LocalDateTime appliedOn,
        int version
){
    public String getCompanyName() {
        return companyDetails.companyName();
    }
    public String getBrn(){
        return companyDetails.brn();
    }
    public String getEmail(){
        return accountAdminDetails.email();
    }
    public String getFirstName(){return accountAdminDetails.firstName();}
    public String getLastName(){return accountAdminDetails.lastName();}
}
