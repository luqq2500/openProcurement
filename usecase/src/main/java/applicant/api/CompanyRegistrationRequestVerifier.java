package applicant.api;

import java.util.UUID;

public interface CompanyRegistrationRequestVerifier {
    void verify(UUID requestId, UUID tokenId, String token);
}
