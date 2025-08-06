package controller.applicant;

import applicant.api.CompanyRegistrationApplier;
import applicant.dto.ApplyCompanyRegistrationRequest;
import applicant.dto.ApplyCompanyRegistrationResponse;
import email.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import otp.OTP;
import otp.OTPService;

import java.util.UUID;

@RestController
public class ApplyCompanyRegistrationController {
    private final CompanyRegistrationApplier applier;
    private final EmailService emailService;
    private final OTPService otpService;

    public ApplyCompanyRegistrationController(CompanyRegistrationApplier applier, EmailService emailService, OTPService otpService) {
        this.applier = applier;
        this.emailService = emailService;
        this.otpService = otpService;
    }

    @PostMapping("/request")
    public void requestCompanyRegistration(@RequestBody @Validated String email){
        try{
            OTP otp = otpService.generate();
            emailService.send(email, "One Time Password", otp.getPassword());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/verify/{id}")
    public void verifyCompanyRegistrationRequest(@RequestBody @Validated String password, @PathVariable UUID id){
        try{
            otpService.validate(id, password);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/apply")
    ResponseEntity<ApplyCompanyRegistrationResponse> applyCompanyRegistration(@RequestBody ApplyCompanyRegistrationRequest request, @RequestParam String email) {
        try{
            applier.apply(request);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
}
