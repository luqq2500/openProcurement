package applicant;

import applicant.api.CompanyRegistrationVerificationRequestor;
import ddd.DomainService;
import event.EventVerificationInitiator;
import applicant.dto.RequestCompanyRegistrationResponse;
import event.EventVerificationRequest;
import token.TokenRequested;

@DomainService
public class RequestCompanyRegistrationVerification implements CompanyRegistrationVerificationRequestor {
    private final EventVerificationInitiator verificationInitiator;

    public RequestCompanyRegistrationVerification(EventVerificationInitiator verificationInitiator) {
        this.verificationInitiator = verificationInitiator;
    }

    @Override
    public RequestCompanyRegistrationResponse requestFrom(String from) {
        EventVerificationRequest request = new EventVerificationRequest(from);
        TokenRequested requestVerification = verificationInitiator.initiate(request);
        return new RequestCompanyRegistrationResponse(requestVerification.tokenId().toString(), from);
    }
}
