package company;

import company.spi.CompanyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryCompanyRepository implements CompanyRepository {
    List<Company> companies = new ArrayList<>();

    @Override
    public void add(Company company) {
        this.companies.add(company);
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Company> findByBusinessRegistrationNumber(String businessRegistrationNumber) {
        return companies.stream()
                .filter(company -> company.getBusinessRegistrationNumber().equals(businessRegistrationNumber))
                .findFirst();
    }

    @Override
    public Optional<Company> findByTaxIdentificationNumber(String taxIdentificationNumber) {
        return companies.stream()
                .filter(company -> company.getTaxIdentificationNumber().equals(taxIdentificationNumber))
                .findFirst();
    }
}
