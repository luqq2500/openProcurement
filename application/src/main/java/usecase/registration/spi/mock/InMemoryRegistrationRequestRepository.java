package usecase.registration.spi.mock;

import annotation.Stub;
import domain.registration.RegistrationRequest;
import usecase.registration.spi.RegistrationRequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Stub
public class InMemoryRegistrationRequestRepository implements RegistrationRequestRepository {
    private final List<RegistrationRequest> requests = new ArrayList<>();
    @Override
    public RegistrationRequest get(UUID id) {
        return requests.stream()
                .filter(request -> request.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    @Override
    public void add(RegistrationRequest registrationRequest) {
        requests.add(registrationRequest);
    }
}
