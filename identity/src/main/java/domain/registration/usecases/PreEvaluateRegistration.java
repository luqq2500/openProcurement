package domain.registration.usecases;

import domain.company.Company;
import domain.company.spi.CompanyRepository;
import domain.employee.Employee;
import domain.employee.spi.EmployeeRepository;
import domain.registration.RegistrationApplication;
import domain.registration.RegistrationApplicationStatus;
import domain.registration.api.RegistrationPreEvaluator;
import domain.registration.spi.RegistrationRepository;

import java.util.Optional;
import java.util.UUID;

public class PreEvaluateRegistration implements RegistrationPreEvaluator {
    private final RegistrationRepository registrationRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyRepository companyRepository;

    public PreEvaluateRegistration(RegistrationRepository registrationRepository, EmployeeRepository employeeRepository, CompanyRepository companyRepository) {
        this.registrationRepository = registrationRepository;
        this.employeeRepository = employeeRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void evaluate(UUID registrationId) {
        RegistrationApplication registration = registrationRepository.getLatest(registrationId);
        Optional<RegistrationApplication> registrationDuplicateBrn = registrationRepository.findLatestByBrn(registration.getBrn());
        Optional<Company> companyDuplicateBrn = companyRepository.findByBrn(registration.getBrn());
        Optional<Employee> employeeDuplicateUsername = employeeRepository.findByUsername(registration.getUsername());
        if ((registrationDuplicateBrn.isPresent() && !registrationDuplicateBrn.get().isRejected()) ||
                companyDuplicateBrn.isPresent() ||
                employeeDuplicateUsername.isPresent()){
            registration.updateStatus(RegistrationApplicationStatus.REJECTED);
        }else{
            registration.updateStatus(RegistrationApplicationStatus.IN_PROGRESS);
        }
        registrationRepository.add(registration);
    }
}