package company.api;

import company.application.command.ApplyCompanyRegistrationCommand;
import company.model.CompanyRegistrationApplication;

public interface CompanyRegistrationApplier {
    public CompanyRegistrationApplication apply(ApplyCompanyRegistrationCommand command);
}
