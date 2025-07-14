package validator.application;
import address.exception.InvalidAddressRuleException;
import address.model.AddressRule;
import address.spi.AddressCountryRuleRepository;
import company.command.ApplyCompanyRegistrationCommand;
import validator.api.CompanyRegistrationApplicationValidator;

public class ValidateCompanyAddressRegistration implements CompanyRegistrationApplicationValidator {
    private final AddressCountryRuleRepository repository;

    public ValidateCompanyAddressRegistration(AddressCountryRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(ApplyCompanyRegistrationCommand command)  {
        AddressRule rule = getAddressRule(command);
        rule.validateState(command.address().state());
        rule.validateCity(command.address().state(), command.address().city());
        rule.validatePostalCode(command.address().state(), command.address().city(), command.address().postalCode());
    }

    private AddressRule getAddressRule(ApplyCompanyRegistrationCommand command) {
        return repository.addressCountryRules().stream()
                .filter(addressRule -> addressRule.country().equals(command.address().country()))
                .findFirst().orElseThrow(() -> new InvalidAddressRuleException(command.address().country() + " is not registered."));
    }
}
