package administration.registration;


import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationApplication (
        UUID applicationId,
        String companyName,
        String brn,
        String structure,
        LocalDateTime appliedOn
){}
