package event;

import ddd.DomainService;
import notification.NotificationMessage;
import notification.NotificationService;
import token.Token;
import token.TokenRequested;
import token.TokenService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@DomainService
public class InitiateEventVerification implements EventVerificationInitiator {
    private final NotificationService notificationService;
    private final TokenService<?> tokenService;

    public InitiateEventVerification(NotificationService notificationService, TokenService<?> tokenService) {
        this.notificationService = notificationService;
        this.tokenService = tokenService;
    }

    @Override
    public TokenRequested initiate(EventVerificationRequest request) {
        Token token = tokenService.request(request.from(), LocalDateTime.now().plusMinutes(15));
        NotificationMessage notificationMessage = new NotificationMessage(
                request.from(),
                "OTP Email Verification",
                String.format("Please complete the email verification with given OTP '%s'. \n Email verification expires in %s",
                        token.getToken(),
                        token.getExpiry().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
        );
        notificationService.send(notificationMessage);
        return new TokenRequested(token.getId(), token.getRequestFrom());
    }
}
