package company.api;

import company.command.ApplyCompanyRegistrationCommand;
import company.CompanyRegistrationApplication;

public interface CompanyRegistrationApplier {
    public CompanyRegistrationApplication apply(ApplyCompanyRegistrationCommand command);
}
