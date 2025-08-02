package applicant.api;
import applicant.dto.ApplyCompanyRegistrationCommand;

public interface CompanyRegistrationApplier {
    void apply(ApplyCompanyRegistrationCommand command);
}
