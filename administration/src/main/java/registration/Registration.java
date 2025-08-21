package registration;

import java.util.UUID;

public record Registration(UUID registrationId, String companyName, String brn, String structure){

}
