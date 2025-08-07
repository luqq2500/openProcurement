package applicant.dto;

import java.util.UUID;

public record RequestCompanyRegistrationResponse(String requestId, String otpId, String oneTimePassword) {
}
