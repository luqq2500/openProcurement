package address.api;

import address.AddressCommand;

public interface AddressValidator {
    void validate(AddressCommand command);
}
