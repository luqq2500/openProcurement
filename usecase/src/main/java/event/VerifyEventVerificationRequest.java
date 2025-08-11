package event;

import java.util.UUID;

public record VerifyEventVerificationRequest(UUID tokenId, String token) {
}
