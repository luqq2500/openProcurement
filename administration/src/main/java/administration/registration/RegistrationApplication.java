package administration.registration;



import administration.address.Address;

import java.time.LocalDateTime;
import java.util.UUID;

public record RegistrationApplication (
        UUID applicationId,
        String companyName,
        Address address,
        String brn,
        String structure,
        LocalDateTime appliedOn
){}
