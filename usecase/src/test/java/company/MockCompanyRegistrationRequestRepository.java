package company;

import company.spi.CompanyRegistrationRequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockCompanyRegistrationRequestRepository implements CompanyRegistrationRequestRepository {
    List<CompanyRegistrationRequest> requests = new ArrayList<CompanyRegistrationRequest>();
    @Override
    public List<CompanyRegistrationRequest> requests() {
        return requests;
    }
    @Override
    public void add(CompanyRegistrationRequest request) {
        requests.add(request);
    }
    @Override
    public Optional<CompanyRegistrationRequest> findById(String requestId) {
        return requests.stream()
                .filter(request -> request.getRequestId().equals(requestId))
                .findFirst();
    }
}
