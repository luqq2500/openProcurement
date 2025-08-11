package verification;

import token.InitiateVerificationResponse;


public interface VerificationInitiator {
    InitiateVerificationResponse initiate(InitiateVerificationRequest request);
}
