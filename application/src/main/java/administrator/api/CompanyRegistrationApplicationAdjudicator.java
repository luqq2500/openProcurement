package administrator.api;

import administrator.model.Administrator;
import company.model.CompanyRegistrationApplication;

public interface CompanyRegistrationApplicationAdjudicator {
    public CompanyRegistrationApplication process(CompanyRegistrationApplication application, Administrator administrator);
    public CompanyRegistrationApplication approve(CompanyRegistrationApplication application, Administrator administrator);
    public CompanyRegistrationApplication reject(CompanyRegistrationApplication application, Administrator administrator);
}
