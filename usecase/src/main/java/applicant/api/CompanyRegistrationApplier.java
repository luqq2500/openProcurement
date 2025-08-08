package applicant.api;
import applicant.dto.ApplyCompanyRegistrationCommand;
import company.CompanyRegistration;

public interface CompanyRegistrationApplier {
    CompanyRegistration apply(ApplyCompanyRegistrationCommand command);
}
