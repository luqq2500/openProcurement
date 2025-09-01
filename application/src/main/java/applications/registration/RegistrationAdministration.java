package applications.registration;



import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationAdministration (
        UUID applicationId, RegistrationStatus status, LocalDateTime administeredOn
){
    public boolean applicableForResubmit(){
        return status.equals(RegistrationStatus.REJECTED);
    }
    public boolean isApproved(){
        return status.equals(RegistrationStatus.APPROVED);
    }
}
