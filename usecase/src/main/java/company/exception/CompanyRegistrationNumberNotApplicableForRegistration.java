package company.exception;

public class CompanyRegistrationNumberNotApplicableForRegistration extends RuntimeException {
    public CompanyRegistrationNumberNotApplicableForRegistration(String message) {
        super(message);
    }
}
