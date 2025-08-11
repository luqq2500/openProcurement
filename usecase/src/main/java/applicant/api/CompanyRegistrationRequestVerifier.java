package applicant.api;

import company.CompanyRegistrationRequest;
import verification.VerifyEventVerificationRequest;

public interface CompanyRegistrationRequestVerifier {
    CompanyRegistrationRequest verify(VerifyEventVerificationRequest verification);
}
