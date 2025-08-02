package company.spi;

import company.CompanyRegistrationRequest;

import java.util.List;

public interface CompanyRegistrationRequestRepository {
    List<CompanyRegistrationRequest> requests();
    void add(CompanyRegistrationRequest request);
}
