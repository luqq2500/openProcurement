package company.api;

import company.Company;

public interface CompanyRegistrator {
    public Company register(String companyName, String companyStructure, String businessRegistrationNumber, String taxNumber);
}
