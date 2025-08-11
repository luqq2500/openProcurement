package applicant;

import applicant.api.CompanyRegistrationRequestVerifier;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import ddd.DomainService;
import verification.VerifyEventVerificationRequest;
import notification.NotificationMessage;
import notification.NotificationService;
import token.Token;
import token.TokenService;

import java.time.LocalDateTime;

@DomainService
public class VerifyCompanyRegistrationRequest implements CompanyRegistrationRequestVerifier {
    private final CompanyRegistrationRequestRepository requestRepository;
    private final TokenService<?> tokenService;
    private final NotificationService notificationService;

    public VerifyCompanyRegistrationRequest(CompanyRegistrationRequestRepository requestRepository, TokenService<?> tokenService, NotificationService notificationService) {
        this.requestRepository = requestRepository;
        this.tokenService = tokenService;
        this.notificationService = notificationService;
    }

    @Override
    public CompanyRegistrationRequest verify(VerifyEventVerificationRequest verification) {
        Token token = tokenService.verify(verification.tokenId(), verification.token());
        CompanyRegistrationRequest request = new CompanyRegistrationRequest(token.getRequestFrom(), LocalDateTime.now().plusDays(7));
        requestRepository.add(request);
        String registrationLink = String.format("openprocurement.com/registrations/apply/%s",request.getId());
        NotificationMessage notificationMessage = new NotificationMessage(
                request.getEmail(),"Company Registration Request Verified",
                String.format("Registration link: %s Link expires in %s",
                        registrationLink, request.getExpiryTime())
        );
        notificationService.send(notificationMessage);
        return request;
    }
}
