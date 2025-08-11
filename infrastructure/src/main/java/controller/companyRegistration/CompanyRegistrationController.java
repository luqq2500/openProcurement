package controller.companyRegistration;

import applicant.api.CompanyRegistrationApplier;
import applicant.api.CompanyRegistrationRequestVerifier;
import verification.InitiateVerificationRequest;
import verification.VerificationInitiator;
import applicant.dto.ApplyCompanyRegistrationCommand;
import applicant.dto.ApplyCompanyRegistrationResponse;
import applicant.dto.VerifyCompanyRegistrationRequestResponse;
import company.CompanyRegistration;
import company.CompanyRegistrationRequest;
import verification.VerifyEventVerificationRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import token.InitiateVerificationResponse;


@RestController
@RequestMapping("/registration")
public class CompanyRegistrationController {
    private final VerificationInitiator verificationInitiator;
    private final CompanyRegistrationRequestVerifier verifier;
    private final CompanyRegistrationApplier applier;

    public CompanyRegistrationController(VerificationInitiator verificationInitiator, CompanyRegistrationRequestVerifier verifier, CompanyRegistrationApplier applier) {
        this.verificationInitiator = verificationInitiator;
        this.verifier = verifier;
        this.applier = applier;
    }

    @PostMapping
    public ResponseEntity<InitiateVerificationResponse> request(@RequestBody @Valid InitiateVerificationRequest request){
        InitiateVerificationResponse token = verificationInitiator.initiate(request);
        InitiateVerificationResponse response = new InitiateVerificationResponse(token.tokenId(), token.from(), token.token());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyCompanyRegistrationRequestResponse> verify(@RequestBody @Valid VerifyEventVerificationRequest request){
        CompanyRegistrationRequest registrationRequest = verifier.verify(request);
        VerifyCompanyRegistrationRequestResponse response = new VerifyCompanyRegistrationRequestResponse(registrationRequest.getId().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/apply")
    public ResponseEntity<ApplyCompanyRegistrationResponse> apply(@RequestBody ApplyCompanyRegistrationCommand apply){
        CompanyRegistration registration = applier.apply(apply);
        ApplyCompanyRegistrationResponse response = new ApplyCompanyRegistrationResponse(registration.getCompanyName(), registration.getStatus().toString());
        return ResponseEntity.ok(response);
    }
}
