package company.model;

import company.exception.InvalidRegisterCompanyCommand;

public class RegisterCompanyCommand {
    private final String companyName;
    private final String registrationNumber;
    private final String taxNumber;
    private final String businessStructure;
    private final String countryCode;
    public RegisterCompanyCommand(CompanyRegistrationApplication application) {
        validateRegistrationApplication(application.companyName(), "Company name cannot be blank");
        validateRegistrationApplication(application.registrationNumber(), "Registration number cannot be blank");
        validateRegistrationApplication(application.taxNumber(), "Tax number cannot be blank");
        validateRegistrationApplication(application.businessStructure(), "Business structure cannot be blank");
        validateRegistrationApplication(application.countryCode(), "Country code cannot be blank");
        this.companyName=application.companyName();
        this.registrationNumber=application.registrationNumber();
        this.taxNumber=application.taxNumber();
        this.businessStructure=application.businessStructure();
        this.countryCode=application.countryCode();
    }

    private static void validateRegistrationApplication(String application, String Company_name_cannot_be_blank) {
        if (application.isBlank()) {
            throw new InvalidRegisterCompanyCommand(Company_name_cannot_be_blank);
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public String getBusinessStructure() {
        return businessStructure;
    }

    public String getCountryCode() {
        return countryCode;
    }
}
