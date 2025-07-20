package company.api;

import company.Company;
import company.CompanyRegistrationApplication;

public interface CompanyRegistrator {
    public Company register(CompanyRegistrationApplication command);
}
