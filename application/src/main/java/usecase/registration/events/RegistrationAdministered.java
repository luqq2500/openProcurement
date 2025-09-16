package usecase.registration.events;

import domain.registration.RegistrationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationAdministered(UUID requestId, RegistrationStatus status, LocalDateTime administeredOn) {
    public boolean canResubmit(){
        return status == RegistrationStatus.REJECTED;
    }
}
