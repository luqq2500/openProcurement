package applicant;

import applicant.api.CompanyRegistrationRequestor;
import applicant.dto.RequestCompanyRegistrationRequest;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;
import token.OTP;
import token.OTPService;

import java.time.format.DateTimeFormatter;


public class RequestCompanyRegistration implements CompanyRegistrationRequestor {
    private final CompanyRegistrationRequestRepository repository;
    private final EmailService emailService;
    private final OTPService OTPService;

    public RequestCompanyRegistration(CompanyRegistrationRequestRepository repository, EmailService emailService, OTPService OTPService) {
        this.repository = repository;
        this.emailService = emailService;
        this.OTPService = OTPService;
    }

    @Override
    public void request(RequestCompanyRegistrationRequest req) {
        CompanyRegistrationRequest request = new CompanyRegistrationRequest(req.email());
        repository.add(request);
        OTP otp = OTPService.generate();
        emailService.send(
                req.email(),
                "OTP Email Verification",
                String.format("""
                        Your request for registration has been requested on %s. \
                        Please complete the email verification with given OTP '%s'. \
                        Email verification expires in %s""",
                        request.getRequestDate(),
                        otp.getPassword(),
                        otp.getExpiration().format(DateTimeFormatter.ISO_TIME)));
    }
}
