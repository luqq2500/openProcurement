package domain.employee.spi;

import domain.account.employee.EmployeeAccount;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<EmployeeAccount> findByUsername(String username);
}
