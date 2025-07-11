package company.api;

import company.model.Company;
import company.model.RegisterCompanyCommand;

@FunctionalInterface
public interface CompanyRegistrator {
    public Company register(RegisterCompanyCommand registerCompanyCommand);
}
