package company.api;
import company.dto.ApplyCompanyRegistrationCommand;

public interface CompanyRegistrationApplier {
    void apply(ApplyCompanyRegistrationCommand command);
}
