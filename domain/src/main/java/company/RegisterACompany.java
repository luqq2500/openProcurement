package company;
import company.api.CompanyRegistrator;
import company.spi.CompanyRepository;

public class RegisterACompany implements CompanyRegistrator {
    private final CompanyRepository companyRepository;
    public RegisterACompany(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company register(String companyName, String companyStructure, String businessRegistrationNumber, String taxIdentificationNumber) {
        validateInformation(companyName, companyStructure, businessRegistrationNumber, taxIdentificationNumber);
        Company company = new Company(companyName, companyStructure, businessRegistrationNumber, taxIdentificationNumber);
        this.companyRepository.add(company);
        return company;
    }

    private void validateInformation(String companyName, String companyStructure, String businessRegistrationNumber, String taxIdentificationNumber) {
        if (companyRepository.findByBusinessRegistrationNumber(businessRegistrationNumber).isPresent()) {
            throw new RuntimeException("Company " + businessRegistrationNumber + "is already exists");
        }
        if (companyRepository.findByTaxIdentificationNumber(taxIdentificationNumber).isPresent()) {
            throw new RuntimeException("Company " + businessRegistrationNumber + "is already exists");
        }
        if (companyName.isBlank()) {
            throw new RuntimeException("Company name is empty");
        }
        if (companyStructure.isBlank()) {
            throw new RuntimeException("Company structure is empty");
        }
        if (businessRegistrationNumber.isBlank()) {
            throw new RuntimeException("Business registration number is empty");
        }
        if (taxIdentificationNumber.isBlank()) {
            throw new RuntimeException("Tax identification number is empty");
        }
        if (!CompanyStructure.COMPANY_STRUCTURES.contains(companyStructure)) {
            throw new RuntimeException("Company " + companyStructure + "is not a valid company structure");
        }
        if (businessRegistrationNumber.length()!=12){
            throw new RuntimeException("Business registration number length must be 12");
        }
        if (taxIdentificationNumber.length()!=12){
            throw new RuntimeException("Tax identification number length must be 12");
        }
        if (!businessRegistrationNumber.matches("\\d+")){
            throw new RuntimeException("Business registration number must only contain numbers");
        }
        if (!taxIdentificationNumber.matches("\\d+")){
            throw new RuntimeException("Tax identification number must only contain numbers");
        }

    }
}
