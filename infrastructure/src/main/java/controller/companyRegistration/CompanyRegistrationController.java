package controller.companyRegistration;

import applicant.api.CompanyRegistrationApplier;
import applicant.api.CompanyRegistrationRequestVerifier;
import event.EventVerificationInitiator;
import applicant.dto.ApplyCompanyRegistrationCommand;
import applicant.dto.ApplyCompanyRegistrationResponse;
import applicant.dto.RequestCompanyRegistrationResponse;
import applicant.dto.VerifyCompanyRegistrationRequestResponse;
import company.CompanyRegistration;
import company.CompanyRegistrationRequest;
import controller.companyRegistration.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import token.TokenRequested;

import java.util.UUID;

@RestController
@RequestMapping("/company-registration")
public class CompanyRegistrationController {
    private final EventVerificationInitiator requestor;
    private final CompanyRegistrationRequestVerifier verifier;
    private final CompanyRegistrationApplier applier;

    public CompanyRegistrationController(EventVerificationInitiator requestor, CompanyRegistrationRequestVerifier verifier, CompanyRegistrationApplier applier) {
        this.requestor = requestor;
        this.verifier = verifier;
        this.applier = applier;
    }

    @PostMapping
    public ResponseEntity<RequestCompanyRegistrationResponse> request(@RequestBody @Valid RequestCompanyRegistrationRequest request){
        TokenRequested token = requestor.initiate(request.email());
        RequestCompanyRegistrationResponse response = new RequestCompanyRegistrationResponse(token.tokenId().toString(), otp.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<VerifyCompanyRegistrationRequestResponse> verify(@RequestBody @Valid VerifyCompanyRegistrationRequestRequest request){
        UUID otpId = UUID.fromString(request.otpId());
        CompanyRegistrationRequest registrationRequest = verifier.verify(otpId, request.password());
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
