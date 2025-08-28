package usecase.registerCompany.events;

import identities.registration.RegistrationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationAdministered(UUID requestId, RegistrationStatus status, LocalDateTime administeredOn) {
    public boolean canResubmit(){
        return status == RegistrationStatus.REJECTED;
    }
}
