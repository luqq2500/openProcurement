package identity.registration;



import address.Address;
import administrator.Administrator;
import administrator.AdministratorRole;
import identity.registration.exception.InvalidRegistrationAdministration;

import java.time.LocalDateTime;
import java.util.UUID;

public record Registration(
        UUID id,
        UUID registrationId,
        String companyName,
        Address address,
        String brn,
        String structure,
        RegistrationStatus status,
        UUID administratorId,
        LocalDateTime administeredOn
){
    public Registration updateStatus(Administrator administrator, RegistrationStatus newStatus) {
        if (administrator.validateRole(AdministratorRole.IDENTITY_ADMINISTRATOR)){
            throw new InvalidRegistrationAdministration("Administrator role is not assigned for this task.");}
        if (this.status.validateStatusSetTo(newStatus)){
            throw new InvalidRegistrationAdministration("Status change is invalid");}
        return new Registration(UUID.randomUUID(), registrationId, companyName, address, brn, structure, newStatus, administrator.getAdministratorId(), LocalDateTime.now());
    }
    public boolean isApproved(){
        return status.equals(RegistrationStatus.APPROVE);
    }
    public boolean isRejected(){
        return status.equals(RegistrationStatus.REJECT);
    }
}
