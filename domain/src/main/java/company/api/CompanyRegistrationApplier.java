package company.api;

import company.model.ApplyCompanyRegistrationCommand;
import company.model.CompanyRegistrationApplication;

public interface CompanyRegistrationApplier {
    public CompanyRegistrationApplication apply(ApplyCompanyRegistrationCommand command);
}
