package token;

import token.otp.MockCustomOTPRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import token.otp.MockCustomOTPService;

import java.time.LocalDateTime;

public class tokenServiceTest {
    @Test
    public void test() {
        TokenRepository<CustomOTP> otpRepository = new MockCustomOTPRepository();
        TokenService<CustomOTP> service = new MockCustomOTPService(otpRepository);
        Token token = service.request("hakimluqq25@gmail.com", LocalDateTime.now());
        Assert.assertNotNull(token);
    }
}
