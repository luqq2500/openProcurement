package company;

import company.api.CompanyRegistrationValidator;
import company.application.ValidateRegisterCompanyIsUnique;
import company.exception.CompanyAlreadyExistException;
import company.model.RegisterCompanyCommand;
import company.spi.CompanyRepository;
import org.junit.Assert;
import org.junit.Test;

public class validateRegisterCompanyIsUniqueTest {
    @Test
    public void registerDuplicateCompanyShouldThrowException() throws CompanyAlreadyExistException {
        CompanyRepository companyRepository = new InMemoryCompanyRepository();
        CompanyRegistrationValidator validator = new ValidateRegisterCompanyIsUnique(companyRepository);

        RegisterCompanyCommand command = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "MY"
        );
        validator.validate(command);
        validator.validate(command);
    }
}
