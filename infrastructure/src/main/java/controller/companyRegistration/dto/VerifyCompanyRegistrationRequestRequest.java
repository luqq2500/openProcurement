package controller.companyRegistration.dto;

import jakarta.validation.constraints.NotBlank;

public record VerifyCompanyRegistrationRequestRequest(
        @NotBlank
        String otpId,
        @NotBlank
        String password
) {
}
