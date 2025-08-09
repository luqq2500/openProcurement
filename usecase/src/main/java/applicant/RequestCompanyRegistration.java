package applicant;

import applicant.api.CompanyRegistrationRequestor;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import ddd.DomainService;
import email.EmailService;
import otp.OTP;
import otp.OTPService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@DomainService
public class RequestCompanyRegistration implements CompanyRegistrationRequestor {
    private final EmailService emailService;
    private final OTPService OTPService;

    public RequestCompanyRegistration(EmailService emailService, OTPService OTPService) {
        this.emailService = emailService;
        this.OTPService = OTPService;
    }

    @Override
    public OTP request(String email) {
        OTP otp = OTPService.requestFor(email, LocalDateTime.now().plusMinutes(15));
        emailService.send(
                email,
                "OTP Email Verification",
                String.format("""
                        Please complete the email verification with given OTP '%s'. \
                        Email verification expires in %s""",
                        otp.getPassword(),
                        otp.getExpiration().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))));
        return otp;
    }
}
