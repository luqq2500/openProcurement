package company;

import company.address.Address;

import java.util.UUID;

public class RegistrationApplication {
    private final UUID id;
    private final String appliedByEmail;
    private final String companyName;
    private final Address address;
    private final String brn;
    private final Structure structure;
    private final String taxNumber;
    private RegistrationStatus status;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;

    public RegistrationApplication(String appliedByEmail, String companyName, Address address, String brn, Structure structure, String taxNumber, String firstName, String lastName, String username, String password) {
        this.appliedByEmail = appliedByEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.id = UUID.randomUUID();
        this.companyName = companyName;
        this.address = address;
        this.brn = brn;
        this.structure = structure;
        this.taxNumber = taxNumber;
        this.status = RegistrationStatus.PENDING;
    }
    public void elevateStatusTo(RegistrationStatus newStatus) {
        this.status.validateStatusUpdateTo(newStatus);
        this.status = newStatus;
    }
    public boolean isUnderReview() {
        return status != RegistrationStatus.APPROVED && status != RegistrationStatus.REJECTED;
    }

    public String getBrn() {
        return brn;
    }

    public String getUsername() {
        return username;
    }

    public UUID getId() {
        return id;
    }

    public String getAppliedByEmail() {
        return appliedByEmail;
    }
}
