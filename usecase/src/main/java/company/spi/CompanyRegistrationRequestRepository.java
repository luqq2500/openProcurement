package company.spi;

import company.CompanyRegistrationRequest;

import java.util.List;
import java.util.Optional;

public interface CompanyRegistrationRequestRepository {
    List<CompanyRegistrationRequest> requests();
    void add(CompanyRegistrationRequest request);
    Optional<CompanyRegistrationRequest> findById(String requestId);
}
