package token;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Token {
    protected final UUID tokenId;
    protected final String requestedFrom;
    protected final String token;
    protected final LocalDateTime expiry;
    protected boolean enabled;

    protected Token(String requestedFrom, String token, LocalDateTime expiry) {
        this.requestedFrom = requestedFrom;
        this.tokenId = UUID.randomUUID();
        this.token = token;
        this.expiry = expiry;
        this.enabled = true;
    }

    public abstract void verify(String token);
    public abstract void validateExpiry();
    public abstract void disable();
    public UUID getId(){return tokenId;};
    public LocalDateTime getExpiry(){return expiry;}
    public String getRequestFrom(){return requestedFrom;}
    public String getToken(){return token;}
    public boolean isEnabled(){return enabled;}
}
