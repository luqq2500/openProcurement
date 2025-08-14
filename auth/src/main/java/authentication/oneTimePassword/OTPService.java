package authentication.oneTimePassword;

public interface OTPService {
    String generate(String from);
    void verify(String otp);
}
