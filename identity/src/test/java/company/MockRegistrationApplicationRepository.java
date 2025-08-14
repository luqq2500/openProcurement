package company;

import company.spi.RegistrationApplicationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockRegistrationApplicationRepository implements RegistrationApplicationRepository {
    List<RegistrationApplication> applications = new ArrayList<>();

    @Override
    public Optional<RegistrationApplication> getByBrn(String brn) {
        return applications.stream()
                .filter(application -> application.getBrn().equals(brn))
                .findFirst();
    }

    @Override
    public void save(RegistrationApplication application) {
        applications.add(application);
    }
}
