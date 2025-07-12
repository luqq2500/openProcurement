package company.spi;

import company.model.CompanyRegistrationApplication;

import java.util.List;

public interface CompanyRegistrationApplicationRepository {
    List<CompanyRegistrationApplication> registrations();
}
