package controller.companyRegistration.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyCompanyRegistrationRequestResponse(
        @NotBlank
        String requestId
) {
}
