package otp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<OTP> findByRequestFrom(String from) {
        return passwords.stream().filter(otp -> otp.getRequestFrom().equals(from)).findFirst();
    }

    @Override
    public void update(OTP otp) {
        OTP oldOtp = get(otp.getId());
        passwords.remove(oldOtp);
        passwords.add(otp);
    }

    @Override
    public List<OTP> passwords() {
        return passwords;
    }
}
