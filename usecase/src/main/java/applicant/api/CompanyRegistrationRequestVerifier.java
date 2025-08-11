package applicant.api;

import company.CompanyRegistrationRequest;
import event.VerifyEventVerificationRequest;

public interface CompanyRegistrationRequestVerifier {
    CompanyRegistrationRequest verify(VerifyEventVerificationRequest verification);
}
