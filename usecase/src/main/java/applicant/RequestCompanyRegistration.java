package applicant;

import applicant.api.CompanyRegistrationRequestor;
import applicant.dto.RequestCompanyRegistrationCommand;
import company.CompanyRegistrationRequest;
import company.spi.CompanyRegistrationRequestRepository;
import notification.NotificationService;
import notification.NotificationCommand;

import java.time.LocalDateTime;

public class RequestCompanyRegistration implements CompanyRegistrationRequestor {
    private final CompanyRegistrationRequestRepository repository;
    private final NotificationService notificationService;
    private final LocalDateTime expiryDuration = LocalDateTime.now().plusDays(3);

    public RequestCompanyRegistration(CompanyRegistrationRequestRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    @Override
    public void request(RequestCompanyRegistrationCommand command) {
        Applicant applicant = new Applicant(command.firstName(), command.lastName(), command.email(), command.phoneNumber());
        CompanyRegistrationRequest request = new CompanyRegistrationRequest(applicant, expiryDuration);
        repository.add(request);
        NotificationCommand notificationCommand = generateNotificationCommand(request, applicant);
        notificationService.notify(notificationCommand);
    }

    private NotificationCommand generateNotificationCommand(CompanyRegistrationRequest request, Applicant applicant) {
        String subject = "Complete Your Company Registration";
        String message = String.format(
                "Dear %s %s,\n\nPlease complete your company registration by filling out the form. \nThis link expires on %s.\n\nThank you,\nProcurement Team",
                applicant.firstName(), applicant.lastName(), request.getExpiryDate()
        );
        return new NotificationCommand(
                request.getApplicant().email(), subject, message
        );
    }
}
