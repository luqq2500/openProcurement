package applicant;

import applicant.api.CompanyRegistrationRequestVerifier;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import ddd.DomainService;
import email.EmailService;
import otp.OTP;
import otp.OTPService;

import java.time.LocalDateTime;
import java.util.UUID;

@DomainService
public class VerifyRequestCompanyRegistration implements CompanyRegistrationRequestVerifier {
    private final CompanyRegistrationRequestRepository requestRepository;
    private final OTPService otpService;
    private final EmailService emailService;

    public VerifyRequestCompanyRegistration(CompanyRegistrationRequestRepository requestRepository, OTPService otpService, EmailService emailService) {
        this.requestRepository = requestRepository;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    @Override
    public CompanyRegistrationRequest verify(UUID otpId, String otp) {
        OTP verifiedOtp = otpService.verify(otpId, otp);
        CompanyRegistrationRequest request = new CompanyRegistrationRequest(verifiedOtp.getRequestFrom(), LocalDateTime.now().plusDays(7));
        requestRepository.add(request);
        String registrationLink = String.format("openprocurement.com/registrations/apply/%s",request.getId());
        emailService.send(
                request.getEmail(),
                "Company Registration Request Verified",
                String.format("Registration link: %s Link expires in %s",
                       registrationLink, request.getExpiryTime())
        );
        return request;
    }
}
