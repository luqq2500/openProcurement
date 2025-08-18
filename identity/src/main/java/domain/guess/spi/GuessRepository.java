package domain.guess.spi;

import domain.guess.Guess;

import java.util.UUID;

public interface GuessRepository {
    Guess get(UUID id);
}
