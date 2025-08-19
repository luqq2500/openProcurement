package domain.employee.spi;

import domain.employee.Employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByEmail(String username);
    void add(Employee employee);
}
