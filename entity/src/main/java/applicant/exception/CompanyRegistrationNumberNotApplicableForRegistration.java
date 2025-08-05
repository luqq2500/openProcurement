package applicant.exception;

public class CompanyRegistrationNumberNotApplicableForRegistration extends RuntimeException {
    public CompanyRegistrationNumberNotApplicableForRegistration(String message) {
        super(message);
    }
}
