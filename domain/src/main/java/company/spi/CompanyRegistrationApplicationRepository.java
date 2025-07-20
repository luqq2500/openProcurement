package company.spi;

import company.CompanyRegistrationApplication;

import java.util.List;

public interface CompanyRegistrationApplicationRepository {
    List<CompanyRegistrationApplication> registrations();
}
