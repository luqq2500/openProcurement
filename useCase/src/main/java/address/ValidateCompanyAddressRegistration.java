package address;
import address.api.AddressValidator;
import address.exception.InvalidAddressRuleException;
import address.spi.AddressCountryRuleRepository;

public class ValidateCompanyAddressRegistration implements AddressValidator {
    private final AddressCountryRuleRepository repository;

    public ValidateCompanyAddressRegistration(AddressCountryRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(AddressCommand command)  {
        AddressRule rule = getAddressRule(command);
        rule.validateState(command.state());
        rule.validateCity(command.state(), command.city());
        rule.validatePostalCode(command.state(), command.city(), command.postalCode());
    }

    private AddressRule getAddressRule(AddressCommand command) {
        return repository.addressCountryRules().stream()
                .filter(addressRule -> addressRule.country().equals(command.country()))
                .findFirst().orElseThrow(() -> new InvalidAddressRuleException(command.country() + " is not registered."));
    }
}
