package token;

import java.util.UUID;

public record InitiateVerificationResponse(UUID tokenId, String from, String token) {}
