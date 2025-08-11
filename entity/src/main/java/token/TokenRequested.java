package token;

import java.util.UUID;

public record TokenRequested(UUID tokenId, String from) {}
