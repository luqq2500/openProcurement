package token;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockOTPRepository implements OTPRepository {
    List<OTP> passwords = new ArrayList<>();
    @Override
    public void add(OTP otp) {
        passwords.add(otp);
    }
    @Override
    public OTP get(UUID id) {
        return passwords.stream().filter(otp -> otp.getId().equals(id)).findFirst().orElseThrow(() -> new InvalidOTP("OTP not found"));
    }
}
