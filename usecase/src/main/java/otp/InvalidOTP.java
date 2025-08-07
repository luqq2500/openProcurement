package otp;

public class InvalidOTP extends RuntimeException {
    public InvalidOTP(String message) {
        super(message);
    }
}
