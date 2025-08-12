package company;

import company.address.Address;

import java.util.UUID;

public class CompanyRegistrationApplication {
    private final UUID id;
    private final String companyName;
    private final Address address;
    private final String registrationNumber;
    private final Structure structure;
    private final String taxNumber;
    private CompanyRegistrationApplicationStatus status;
    private UUID assessedByAdministratorId;
    public CompanyRegistrationApplication(String companyName, Address address, String registrationNumber, Structure structure, String taxNumber) {
        this.id = UUID.randomUUID();
        this.companyName = companyName;
        this.address = address;
        this.registrationNumber = registrationNumber;
        this.structure = structure;
        this.taxNumber = taxNumber;
        this.status = CompanyRegistrationApplicationStatus.PENDING;
    }
    public void updateStatusTo(CompanyRegistrationApplicationStatus newStatus) {
        this.status.validateStatusUpdateTo(newStatus);
        this.status = newStatus;
    }
}
