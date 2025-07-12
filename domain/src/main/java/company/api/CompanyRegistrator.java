package company.api;

import company.model.Company;

public interface CompanyRegistrator {
    public Company register(String companyName, String registrationNumber, String taxNumber, String businessStructure, String countryCode);
}
