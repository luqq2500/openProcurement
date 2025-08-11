package token;

public class InvalidOTP extends RuntimeException {
    public InvalidOTP(String s) {
        super(s);
    }
}
