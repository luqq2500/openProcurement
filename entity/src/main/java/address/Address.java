package address;

public record Address (
        String streetAddress1,
        String streetAddress2,
        String streetAddress3,
        String city,
        String postalCode,
        String state,
        Country country
) {
}
