package registration.spi;

import registration.RegistrationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryRegistrationRequestRepository implements RegistrationRequestRepository {
    private final List<RegistrationRequest> requests = new ArrayList<>();
    @Override
    public RegistrationRequest getById(UUID id) {
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
