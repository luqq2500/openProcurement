package applicant;

import applicant.api.CompanyRegistrationRequestor;
import applicant.dto.RequestCompanyRegistrationRequest;
import applicant.dto.RequestCompanyRegistrationResponse;
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
    public RequestCompanyRegistrationResponse request(RequestCompanyRegistrationRequest req) {
        CompanyRegistrationRequest request = new CompanyRegistrationRequest(req.email());
        repository.add(request);
        OTP otp = OTPService.requestFor(req.email());
        emailService.send(
                req.email(),
                "OTP Email Verification",
                String.format("""
                        Your request for registration has been requested on %s. \
                        Please complete the email verification with given OTP '%s'. \
                        Email verification expires in %s""",
                        request.getRequestDate(),
                        otp.getPassword(),
                        otp.getExpiration().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))));
        return new RequestCompanyRegistrationResponse(request.getId().toString(), otp.getId().toString(), otp.getPassword());
    }
}
