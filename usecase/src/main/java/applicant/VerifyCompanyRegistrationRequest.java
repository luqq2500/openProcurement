package applicant;

import applicant.api.CompanyRegistrationRequestVerifier;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;
import token.OTPService;

import java.time.LocalDateTime;
import java.util.UUID;

public class VerifyCompanyRegistrationRequest implements CompanyRegistrationRequestVerifier {
    private final CompanyRegistrationRequestRepository requestRepository;
    private final OTPService otpService;
    private final EmailService emailService;

    public VerifyCompanyRegistrationRequest(CompanyRegistrationRequestRepository requestRepository, OTPService otpService, EmailService emailService) {
        this.requestRepository = requestRepository;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @Override
    public void verify(UUID requestId, UUID tokenId, String token) {
        otpService.verify(tokenId, token);
        CompanyRegistrationRequest request = requestRepository.get(requestId);
        request.enable(LocalDateTime.now(), LocalDateTime.now().plusMinutes(15));
        emailService.send(
                request.getEmail(),
                "Company Registration Link",
                "link expires in " + request.getExpiryTime()
        );
    }
}
