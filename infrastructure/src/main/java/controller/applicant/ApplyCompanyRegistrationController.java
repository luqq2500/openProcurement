package controller.applicant;

import applicant.api.CompanyRegistrationApplier;
import notification.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import otp.OTPService;

@RestController
@RequestMapping
public class ApplyCompanyRegistrationController {
    private final CompanyRegistrationApplier applier;
    private final NotificationService notificationService;
    private final OTPService otpService;

    public ApplyCompanyRegistrationController(CompanyRegistrationApplier applier, NotificationService notificationService, OTPService otpService) {
        this.applier = applier;
        this.notificationService = notificationService;
        this.otpService = otpService;
    }
}
