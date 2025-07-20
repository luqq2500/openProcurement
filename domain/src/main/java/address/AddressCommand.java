package address;

import address.exception.InvalidAddressException;

public record AddressCommand(
        String line1,
        String line2,
        String line3,
        String city,
        String state,
        String postalCode,
        String country)
{
    public AddressCommand {
        if(line1==null || line1.isBlank()){
            throw new InvalidAddressException("Line 1 cannot be empty");
        }
        if(city==null || city.isBlank()){
            throw new InvalidAddressException("City cannot be empty");
        }
        if(postalCode==null || postalCode.isBlank()){
            throw new InvalidAddressException("Postal Code cannot be empty");
        }
        if(country ==null || country.isBlank()){
            throw new InvalidAddressException("Country Code cannot be empty");
        }
    }
}
