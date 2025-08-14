package company.address;

public record Address(
        String street1,
        String street2,
        String street3,
        String city,
        String zip,
        String state,
        Country country
) {
}
