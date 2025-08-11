package token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository <T extends Token> {
    void add(T token);
    void update(T token);
    Optional<T> findByRequestFrom(String requestFrom);
    T get(UUID id);
    List<T> tokens();
}
