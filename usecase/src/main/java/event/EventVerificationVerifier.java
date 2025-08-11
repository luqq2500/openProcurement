package event;

import company.CompanyRegistrationRequest;

public interface EventVerificationVerifier {
    CompanyRegistrationRequest verify(VerifyEventVerificationRequest verification);
}
