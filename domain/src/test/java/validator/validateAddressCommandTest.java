package validator;

import address.exception.InvalidAddressRuleException;
import address.model.*;
import company.application.command.ApplyCompanyRegistrationCommand;
import org.junit.Assert;
import org.junit.Test;
import validator.api.CompanyRegistrationApplicationValidator;
import validator.application.ValidateCompanyAddressRegistration;

import java.util.*;

public class validateAddressCommandTest {
    @Test
    public void validAddressShouldNotThrowException() {
        Set<PostalCode> postalCodes = Set.of(new PostalCode("43900"));
        Set<City> cities = Set.of(new City("Sepang", postalCodes));
        Set<State> states = Set.of(new State("Selangor", cities));
        AddressRule malaysiaAddressRule = new AddressRule("Malaysia", states);
        var countryRule = List.of(malaysiaAddressRule);

        AddressCommand addressCommand = new AddressCommand("1, Taman Dahlia", "B.B. Salak Tinggi", null,
                "Sepang",
                "Selangor",
                "43900",
                "Malaysia"
        );
        ApplyCompanyRegistrationCommand applicationCommand = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061677",
                "Cooperative",
                addressCommand
        );
        CompanyRegistrationApplicationValidator validator = new ValidateCompanyAddressRegistration(()->countryRule);
        validator.validate(applicationCommand);
    }

    @Test
    public void invalidAddressShouldThrowException() {
        Set<PostalCode> postalCodes = Set.of(new PostalCode("43900"));
        Set<City> cities = Set.of(new City("Sepang", postalCodes));
        Set<State> states = Set.of(new State("Selangor", cities));
        AddressRule malaysiaAddressRule = new AddressRule("Malaysia", states);
        var countryRule = List.of(malaysiaAddressRule);

        AddressCommand addressCommand = new AddressCommand("1, Taman Dahlia", "B.B. Salak Tinggi", null,
                "Nilai",
                "Selangor",
                "53100",
                "Malaysia"
        );
        ApplyCompanyRegistrationCommand applicationCommand = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061677",
                "Cooperative",
                addressCommand
        );
        CompanyRegistrationApplicationValidator validator = new ValidateCompanyAddressRegistration(()->countryRule);
        Assert.assertThrows(InvalidAddressRuleException.class, ()->validator.validate(applicationCommand));
    }

}
