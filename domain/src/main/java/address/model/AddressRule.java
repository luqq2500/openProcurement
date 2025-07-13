package address.model;

import address.exception.InvalidAddressRuleException;

import java.util.Objects;
import java.util.Set;


public record AddressRule(String country, Set<State> states) {
    public AddressRule {
        if (Objects.isNull(country)) throw new InvalidAddressRuleException("Country name is required.");
        if (Objects.isNull(states)) throw new InvalidAddressRuleException("States list is required.");
    }
    public void validateState(String stateName){
        if (stateName == null || stateName.trim().isBlank()) {
            throw new InvalidAddressRuleException("State name is required.");
        }
        states().stream()
                .filter(state -> state.stateName().equals(stateName))
                .findFirst()
                .orElseThrow(() -> new InvalidAddressRuleException("State " + stateName + " does not belong to country " + country));
    }

    public void validateCity(String stateName, String cityName){
        if (cityName == null || cityName.trim().isBlank()) {
            throw new InvalidAddressRuleException("City name is required.");
        }
        State state = getState(stateName);
        state.cities().stream()
                .filter(city -> city.cityName().equals(cityName))
                .findFirst()
                .orElseThrow(() -> new InvalidAddressRuleException("City " + cityName + " does not belong to state " + stateName + " ."));
    }

    public void validatePostalCode(String stateName, String cityName, String postalCode){
        if (postalCode == null || postalCode.trim().isBlank()) {
            throw new InvalidAddressRuleException("Postal code is required.");
        }
        City city = getCity(stateName, cityName);
        if (city.postalCodes().stream().noneMatch(pc -> pc.postalCode().equals(postalCode))) {
            throw new InvalidAddressRuleException("Postal code " + postalCode + " does not belong to city " + cityName + " .");
        }
    }

    private State getState(String stateName) {
        return states.stream()
                .filter(s -> s.stateName().equals(stateName))
                .findFirst()
                .orElseThrow(()-> new InvalidAddressRuleException("State " + stateName + " does not belong to country " + country));
    }

    private City getCity(String stateName, String cityName) {
        return states.stream()
                .filter(s -> s.stateName().equals(stateName))
                .flatMap(s -> s.cities().stream())
                .filter(c -> c.cityName().equals(cityName))
                .findFirst()
                .orElseThrow(() -> new InvalidAddressRuleException("City " + cityName + " does not belong to state " + stateName));
    }
}
