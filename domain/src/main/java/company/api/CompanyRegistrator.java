package company.api;

import company.model.Company;

public interface CompanyRegistrator {
    public Company register(String name, String structure, String brn, String tin);
}
