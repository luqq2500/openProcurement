package company.api;

import company.model.Company;
import company.model.CompanyRegistrationApplication;

public interface CompanyRegistrator {
    public Company register(CompanyRegistrationApplication command);
}
