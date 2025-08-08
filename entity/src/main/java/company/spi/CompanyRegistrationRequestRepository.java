package company.spi;

import company.CompanyRegistrationRequest;

import java.util.UUID;

public interface CompanyRegistrationRequestRepository {
    void add(CompanyRegistrationRequest request);
    CompanyRegistrationRequest get(UUID requestId);
}
