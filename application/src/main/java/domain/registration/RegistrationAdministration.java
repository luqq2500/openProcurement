package domain.registration;



import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationAdministration (
        UUID registrationId, RegistrationStatus status, LocalDateTime administeredOn, int version
){
    public boolean applicableForResubmit(){
        return status.equals(RegistrationStatus.REJECTED);
    }
    public boolean isApproved(){
        return status.equals(RegistrationStatus.APPROVED);
    }
}
