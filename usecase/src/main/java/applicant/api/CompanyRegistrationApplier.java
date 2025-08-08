package applicant.api;
import applicant.dto.ApplyCompanyRegistrationRequest;
import company.CompanyRegistration;

public interface CompanyRegistrationApplier {
    CompanyRegistration apply(ApplyCompanyRegistrationRequest command);
}
