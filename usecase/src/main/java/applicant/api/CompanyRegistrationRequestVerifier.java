package applicant.api;

import company.CompanyRegistrationRequest;

import java.util.UUID;

public interface CompanyRegistrationRequestVerifier {
    CompanyRegistrationRequest verify(UUID requestId, UUID tokenId, String token);
}
