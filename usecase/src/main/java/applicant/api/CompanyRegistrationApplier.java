package applicant.api;
import applicant.dto.ApplyCompanyRegistrationRequest;
import applicant.dto.ApplyCompanyRegistrationResponse;

public interface CompanyRegistrationApplier {
    ApplyCompanyRegistrationResponse apply(ApplyCompanyRegistrationRequest command);
}
