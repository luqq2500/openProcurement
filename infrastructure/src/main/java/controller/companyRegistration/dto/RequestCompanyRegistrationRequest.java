package controller.companyRegistration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestCompanyRegistrationRequest(@NotBlank @Email String email) {
}
