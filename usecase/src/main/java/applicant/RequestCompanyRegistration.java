package applicant;

import applicant.api.CompanyRegistrationRequestor;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import ddd.DomainService;
import email.EmailService;
import otp.OTP;
import otp.OTPService;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@DomainService
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
    public OTP request(String email) {
        CompanyRegistrationRequest request = new CompanyRegistrationRequest(email);
        repository.add(request);
        OTP otp = OTPService.requestFor(email);
        emailService.send(
                email,
                "OTP Email Verification",
                String.format("""
                        Your request for registration has been requested on %s. \
                        Please complete the email verification with given OTP '%s'. \
                        Email verification expires in %s""",
                        request.getRequestDate(),
                        otp.getPassword(),
                        otp.getExpiration().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))));
        return otp;
    }
}
