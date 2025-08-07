package applicant.api;

import applicant.dto.VerifyCompanyRegistrationRequestResponse;
import company.CompanyRegistrationRequest;

import java.util.UUID;

public interface CompanyRegistrationRequestVerifier {
    VerifyCompanyRegistrationRequestResponse verify(UUID requestId, UUID tokenId, String token);
}
