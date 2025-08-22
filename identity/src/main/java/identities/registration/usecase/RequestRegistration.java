package identities.registration.usecase;

import identities.guess.Guess;
import identities.guess.spi.GuessRepository;
import identities.registration.RegistrationRequest;
import identities.registration.events.RegistrationRequested;
import identities.registration.api.RegistrationRequestor;
import identities.registration.spi.RegistrationRequestRepository;
import port.IntegrationEventHandler;

import java.util.UUID;

public class RequestRegistration implements RegistrationRequestor {
    private final RegistrationRequestRepository requestRepository;
    private final GuessRepository guessRepository;
    private final IntegrationEventHandler<RegistrationRequested> integrationEventHandler;

    public RequestRegistration(RegistrationRequestRepository requestRepository, GuessRepository guessRepository, IntegrationEventHandler<RegistrationRequested> integrationEventHandler) {
        this.requestRepository = requestRepository;
        this.guessRepository = guessRepository;
        this.integrationEventHandler = integrationEventHandler;
    }
    @Override
    public void request(UUID guessAccountId) {
        Guess guess = guessRepository.get(guessAccountId);
        requestRepository.add(new RegistrationRequest(guess.getId()));
        integrationEventHandler.handle(new RegistrationRequested(guess.getEmail()));
    }
}
