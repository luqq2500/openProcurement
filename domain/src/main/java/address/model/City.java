package address.model;

import address.exception.InvalidAddressException;

import java.util.Set;

public record City(String cityName, Set<PostalCode> postalCodes) {
    public City {
        if (cityName == null || cityName.trim().isEmpty()) {
            throw new InvalidAddressException("City name cannot be null or empty");
        }
        if (postalCodes == null) {
            throw new InvalidAddressException("Postal codes cannot be null for city: " + cityName);
        }
    }
}
