package company;

import company.exception.CompanyRegistrationRequestNotFound;
import company.spi.CompanyRegistrationRequestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockCompanyRegistrationRequestRepository implements CompanyRegistrationRequestRepository {
    List<CompanyRegistrationRequest> requests = new ArrayList<>();

    @Override
    public void add(CompanyRegistrationRequest request) {
        requests.add(request);
    }
    @Override
    public CompanyRegistrationRequest get(UUID requestId) {
        return requests.stream().filter(r -> r.getId().equals(requestId)).findFirst().orElseThrow(()->new CompanyRegistrationRequestNotFound("Request not found"));
    }
}
