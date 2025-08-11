package token;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TokenService <T extends Token> {
    T request(String destination, LocalDateTime expiry);
    T verify(UUID tokenId, String token);
}
