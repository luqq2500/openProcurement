package address;

import address.exception.InvalidAddressException;

import java.util.Set;


public record State(
        String stateName,
        Set<City> cities
){
    public State {
        if (stateName == null || stateName.trim().isEmpty()) {
            throw new InvalidAddressException("State name cannot be null or empty");
        }
        if (cities == null) {
            throw new InvalidAddressException("Cities list cannot be null for state: " + stateName);
        }
    }
}
