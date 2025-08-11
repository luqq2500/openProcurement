package event;

import token.TokenRequested;


public interface EventVerificationInitiator {
    TokenRequested initiate(EventVerificationRequest request);
}
