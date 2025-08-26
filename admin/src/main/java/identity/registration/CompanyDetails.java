package identity.registration;


import address.Address;

public record CompanyDetails(
        String companyName, Address address, String brn, String structure) {}
