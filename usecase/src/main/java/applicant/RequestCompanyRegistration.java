package applicant;

import applicant.api.CompanyRegistrationRequestor;
import applicant.dto.RequestCompanyRegistrationCommand;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import email.EmailService;

public class RequestCompanyRegistration implements CompanyRegistrationRequestor {
    private final CompanyRegistrationRequestRepository repository;
    private final EmailService emailService;

    public RequestCompanyRegistration(CompanyRegistrationRequestRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }

    @Override
    public void request(RequestCompanyRegistrationCommand command) {
        command.validateEmailFormat();
        Applicant applicant = new Applicant(command.firstName(), command.lastName(), command.email(), command.phoneNumber());
        CompanyRegistrationRequest request = new CompanyRegistrationRequest(applicant);
        repository.add(request);
        String subject = "Complete Your Company Registration";
        String applicationLink = generateApplicationLink(request.getRequestId());
        String message = String.format(
                "Dear %s %s,\n\nPlease complete your company registration by filling out the form: %s\nThis link expires on %s.\n\nThank you,\nProcurement Team",
                applicant.firstName(), applicant.lastName(),
                applicationLink, request.getExpiryOn()
        );
        emailService.email(command.email(),subject,message );
    }
    private String generateApplicationLink(String requestId) {
        return "https://openprocurement.com/register/request/company?requestId=" + requestId;
    }
}
