package identities.guess.spi;

import identities.guess.Guess;

import java.util.UUID;

public interface GuessRepository {
    Guess get(UUID id);
}
