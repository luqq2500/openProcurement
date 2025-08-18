package domain.employee.spi;

import domain.employee.Employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByUsername(String username);
}
