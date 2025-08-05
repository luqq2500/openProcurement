package address;

public record Address (
        String streetAddress1,
        String streetAddress2,
        String city,
        String postalCode,
        String state,
        Country country
) {
}
