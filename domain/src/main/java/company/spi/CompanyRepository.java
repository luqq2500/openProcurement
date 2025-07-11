package company.spi;

import company.model.Company;

import java.util.stream.Stream;

@FunctionalInterface
public interface CompanyRepository {
    Stream<Company> companies();
}
