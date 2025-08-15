package company;

import company.address.Address;

import java.util.UUID;

public class RegistrationApplication {
    private final UUID id;
    private final String companyName;
    private final Address address;
    private final String brn;
    private final Structure structure;
    private final String taxNumber;
    private RegistrationApplicationStatus status;
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;

    public RegistrationApplication(String companyName, Address address, String brn, Structure structure, String taxNumber, String firstName, String lastName, String username, String password) {
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
        this.status = RegistrationApplicationStatus.PENDING;
    }
    public void elevateStatusTo(RegistrationApplicationStatus newStatus) {
        this.status.validateStatusUpdateTo(newStatus);
        this.status = newStatus;
    }
    public boolean isUnderReview() {
        return status != RegistrationApplicationStatus.APPROVED && status != RegistrationApplicationStatus.REJECTED;
    }

    public String getBrn() {
        return brn;
    }

    public String getUsername() {
        return username;
    }
}
