package company.api;

import company.model.RegisterCompanyCommand;
import company.model.Company;

public interface CompanyRegistrator {
    public Company register(RegisterCompanyCommand command);
}
