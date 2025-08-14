package company;

import company.spi.RegistrationRequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockRegistrationRequestRepository implements RegistrationRequestRepository {
    List<RegistrationRequest> requests = new ArrayList<>();
    @Override
    public RegistrationRequest getById(UUID id) {
        return requests.stream()
                .filter(requests -> requests.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(RegistrationRequest registrationRequest) {
        requests.add(registrationRequest);
    }
}
