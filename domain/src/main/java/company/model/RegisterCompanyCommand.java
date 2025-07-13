package company.model;

import address.model.AddressCommand;
import company.exception.InvalidRegisterCompanyCommand;

public class RegisterCompanyCommand {
    private final String companyName;
    private final String registrationNumber;
    private final String taxNumber;
    private final String businessStructure;
    private final AddressCommand address;
    public RegisterCompanyCommand(CompanyRegistrationApplication application) {
        validateRegistrationApplication(application.companyName(), "Company name cannot be blank");
        validateRegistrationApplication(application.registrationNumber(), "Registration number cannot be blank");
        validateRegistrationApplication(application.taxNumber(), "Tax number cannot be blank");
        validateRegistrationApplication(application.businessStructure(), "Business structure cannot be blank");
        this.companyName=application.companyName();
        this.registrationNumber=application.registrationNumber();
        this.taxNumber=application.taxNumber();
        this.businessStructure=application.businessStructure();
        this.address =application.address();
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


    public AddressCommand getAddress() {
        return address;
    }
}
