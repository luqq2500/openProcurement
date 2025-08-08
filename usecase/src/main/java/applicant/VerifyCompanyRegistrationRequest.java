package applicant;

import applicant.api.CompanyRegistrationRequestVerifier;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import ddd.DomainService;
import email.EmailService;
import otp.OTPService;

import java.time.LocalDateTime;
import java.util.UUID;

@DomainService
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
    public CompanyRegistrationRequest verify(UUID requestId, UUID otpId, String otp) {
        otpService.verify(otpId, otp);
        CompanyRegistrationRequest request = requestRepository.get(requestId);
        String registrationLink = String.format("openprocurement.com/registrations/requests/%s",requestId);
        request.enable(registrationLink, LocalDateTime.now().plusDays(7));
        emailService.send(
                request.getEmail(),
                "Company Registration Request Verified",
                String.format("Registration link: %s Link expires in %s",
                        request.getRegistrationLink(), request.getExpiryTime())
        );
        return request;
    }
}
