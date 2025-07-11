package company;

import company.api.CompanyRegistrationValidator;
import company.application.ValidateRegisterCompanyIsUnique;
import company.exception.CompanyAlreadyExistException;
import company.model.RegisterCompanyCommand;
import company.spi.CompanyRepository;
import org.junit.Before;
import org.junit.Test;

public class validateRegisterCompanyIsUniqueTest {
    private CompanyRegistrationValidator validator;

    @Before
    public void setUp(){
        CompanyRepository companyRepository = new InMemoryCompanyRepository();
        this.validator = new ValidateRegisterCompanyIsUnique(companyRepository);
    }
    @Test
    public void registerDuplicateCompanyShouldThrowException() throws CompanyAlreadyExistException {
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

    @Test
    public void registerAUniqueCompanyShouldNotThrowException(){
        RegisterCompanyCommand command1 = new RegisterCompanyCommand(
                "ForgeNet",
                "202380061600",
                "202380061600",
                "Cooperative",
                "MY"
        );
        validator.validate(command1);
        RegisterCompanyCommand command2 = new RegisterCompanyCommand(
                "ForgeNet",
                "202080061600",
                "202080061600",
                "Cooperative",
                "MY"
        );
        validator.validate(command2);
    }
}
