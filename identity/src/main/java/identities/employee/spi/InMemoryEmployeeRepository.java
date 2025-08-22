package identities.employee.spi;

import identities.employee.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryEmployeeRepository implements EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();
    @Override
    public Optional<Employee> findByEmail(String email) {
        return employees.stream()
                .filter(employee -> employee.getEmail().equals(email))
                .findFirst();
    }
    @Override
    public void add(Employee employee) {
        employees.add(employee);
    }
}
