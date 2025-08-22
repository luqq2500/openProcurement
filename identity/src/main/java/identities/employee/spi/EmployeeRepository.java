package identities.employee.spi;

import identities.employee.Employee;

import java.util.Optional;

public interface EmployeeRepository {
    Optional<Employee> findByEmail(String username);
    void add(Employee employee);
}
