package address;

import address.exception.InvalidAddressException;

public record PostalCode(String postalCode) {
    public PostalCode {
        if (postalCode == null || postalCode.trim().isEmpty()) {
            throw new InvalidAddressException("Postal code cannot be null or empty");
        }
    }
}
