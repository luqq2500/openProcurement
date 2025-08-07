package controller.applicant;

import applicant.api.CompanyRegistrationApplier;
import email.EmailService;
import org.springframework.web.bind.annotation.*;
import otp.OTPService;

@RestController
public class ApplyCompanyRegistrationController {
    private final CompanyRegistrationApplier applier;
    private final EmailService emailService;
    private final OTPService OTPService;

    public ApplyCompanyRegistrationController(CompanyRegistrationApplier applier, EmailService emailService, OTPService OTPService) {
        this.applier = applier;
        this.emailService = emailService;
        this.OTPService = OTPService;
    }
}
