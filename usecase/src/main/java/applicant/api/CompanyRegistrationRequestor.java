package applicant.api;

import otp.OTP;


public interface CompanyRegistrationRequestor {
    OTP request(String email);
}
