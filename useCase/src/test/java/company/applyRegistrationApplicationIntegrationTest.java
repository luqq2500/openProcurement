package company;

import address.*;
import address.api.AddressValidator;
import address.api.CompanyCountryRegistrationRuleValidator;
import address.exception.InvalidAddressRuleException;
import company.command.ApplyCompanyRegistrationCommand;
import company.api.CompanyRegistrationApplier;
import company.exception.CompanyRegistrationApplicationAlreadyExist;
import company.exception.InvalidCompanyRegistrationApplicationCommand;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class applyRegistrationApplicationIntegrationTest {
    private CompanyRegistrationApplier applier;

    @Before
    public void setUp() {
        var applications = List.of(
                new CompanyRegistrationApplication(UUID.randomUUID().toString(), "Cathedral", "202380071700", "202380071700", "Cooperative", new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Sepang",
                        "Selangor",
                        "43900",
                        "Malaysia"
                ), LocalDateTime.now(), "Pending", null, null)
        );
        var countryRegistrationRules = List.of(
                new CompanyRegistrationCountryRule("Malaysia", 12, "\\d{12}", 12 , "\\d{12}",
                        List.of("Sole Proprietorship", "Partnership", "Limited Liability Company (LLC)", "Corporation", "Cooperative"))
        );
        Set<PostalCode> postalCodes = Set.of(new PostalCode("43900"));
        Set<City> cities = Set.of(new City("Sepang", postalCodes));
        Set<State> states = Set.of(new State("Selangor", cities));
        AddressRule countryRules = new AddressRule("Malaysia", states);
        var addressRule = List.of(countryRules);
        CompanyCountryRegistrationRuleValidator countryRuleValidator = new ValidateCompanyCountryRegistrationRule(()->countryRegistrationRules);
        AddressValidator addressRuleValidator = new ValidateCompanyAddressRegistration(()->addressRule);
        this.applier = new ApplyCompanyRegistrationApplication(()->applications, addressRuleValidator, countryRuleValidator);
    }
    @Test
    public void applyRegistrationApplicationShouldReturn(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Sepang",
                        "Selangor",
                        "43900",
                        "Malaysia"
                )
        );
        CompanyRegistrationApplication application = applier.apply(command);
        Assert.assertNotNull(application);
        System.out.println(application);
    }
    @Test
    public void applyDuplicateRegistrationNumberShouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Cathedral",
                "202380071700",
                "202380071500",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Sepang",
                        "Selangor",
                        "43900",
                        "Malaysia"
                )
        );
        CompanyRegistrationApplicationAlreadyExist exception = Assert.assertThrows(CompanyRegistrationApplicationAlreadyExist.class, ()-> applier.apply(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyDuplicateTaxNumberShouldThrowException(){
        ApplyCompanyRegistrationCommand command = new ApplyCompanyRegistrationCommand(
                "Cathedral",
                "202380071788",
                "202380071700",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Sepang",
                        "Selangor",
                        "43900",
                        "Malaysia"
                )
        );
        CompanyRegistrationApplicationAlreadyExist exception = Assert.assertThrows(CompanyRegistrationApplicationAlreadyExist.class, ()-> applier.apply(command));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyInvalidCompanyNameShouldThrowException(){
        InvalidCompanyRegistrationApplicationCommand exception = Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, () -> new ApplyCompanyRegistrationCommand(
                "",
                "202380061600",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "43900",
                        "Malaysia"
                )
        ));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyInvalidCompanyRegistrationNumberShouldThrowException(){
        InvalidCompanyRegistrationApplicationCommand exception = Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, () -> new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Sepang",
                        "Selangor",
                        "43900",
                        "Malaysia"
                )
        ));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyInvalidTaxNumberShouldThrowException(){
        InvalidCompanyRegistrationApplicationCommand exception = Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, () -> new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "43900",
                        "Malaysia"
                )
        ));
        System.out.println(exception.getMessage());
    }

    @Test
    public void applyInvalidBusinessStructureShouldThrowException() {
        InvalidCompanyRegistrationApplicationCommand exception = Assert.assertThrows(InvalidCompanyRegistrationApplicationCommand.class, () -> new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Petaling Jaya",
                        "Selangor",
                        "43900",
                        "Malaysia"
                )
        ));
        System.out.println(exception.getMessage());
    }

    @Test
    public void registerInvalidCountryShouldThrowException(){
        InvalidAddressRuleException exception = Assert.assertThrows(InvalidAddressRuleException.class, () -> applier.apply( new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Sepang",
                        "Selangor",
                        "43900",
                        "Indonesia"
                )
        )));
        System.out.println(exception.getMessage());
    }

    @Test
    public void registerInvalidStateShouldThrowException(){
        InvalidAddressRuleException exception = Assert.assertThrows(InvalidAddressRuleException.class, () -> applier.apply( new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Sepang",
                        "Terengganu",
                        "43900",
                        "Malaysia"
                )
        )));
        System.out.println(exception.getMessage());
    }

    @Test
    public void registerInvalidCityShouldThrowException(){
        InvalidAddressRuleException exception = Assert.assertThrows(InvalidAddressRuleException.class, () -> applier.apply( new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Nilai",
                        "Selangor",
                        "43900",
                        "Malaysia"
                )
        )));
        System.out.println(exception.getMessage());
    }

    @Test
    public void registerInvalidPostalCodeShouldThrowException(){
        InvalidAddressRuleException exception = Assert.assertThrows(InvalidAddressRuleException.class, () -> applier.apply( new ApplyCompanyRegistrationCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                new AddressCommand(
                        "1, Jalan Dahlia 8/2, Taman Dahlia",
                        "Bandar Baru Salak Tinggi",
                        null,
                        "Sepang",
                        "Selangor",
                        "43100",
                        "Malaysia"
                )
        )));
        System.out.println(exception.getMessage());
    }
}
