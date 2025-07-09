package company.api;

import company.model.Company;
import company.model.RegisterCompanyCommand;

public interface CompanyRegistrator {
    public Company register(RegisterCompanyCommand registerCompanyCommand);
}
