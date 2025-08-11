package verification;

import ddd.DomainService;
import notification.NotificationMessage;
import notification.NotificationService;
import token.Token;
import token.InitiateVerificationResponse;
import token.TokenService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@DomainService
public class InitiateVerification implements VerificationInitiator {
    private final NotificationService notificationService;
    private final TokenService<?> tokenService;

    public InitiateVerification(NotificationService notificationService, TokenService<?> tokenService) {
        this.notificationService = notificationService;
        this.tokenService = tokenService;
    }

    @Override
    public InitiateVerificationResponse initiate(InitiateVerificationRequest request) {
        Token token = tokenService.request(request.from(), LocalDateTime.now().plusMinutes(15));
        NotificationMessage notificationMessage = new NotificationMessage(
                request.from(),
                token.getClass().getName() + " Verification",
                String.format("Please complete the email verification with given %s '%s'. \n Email verification expires in %s",
                        token.getClass().getName(),
                        token.getToken(),
                        token.getExpiry().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
        );
        notificationService.send(notificationMessage);
        return new InitiateVerificationResponse(token.getId(), token.getRequestFrom(), token.getToken());
    }
}
