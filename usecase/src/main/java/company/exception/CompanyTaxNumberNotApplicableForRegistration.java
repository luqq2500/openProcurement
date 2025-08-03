package company.exception;

public class CompanyTaxNumberNotApplicableForRegistration extends RuntimeException {
    public CompanyTaxNumberNotApplicableForRegistration(String message) {
        super(message);
    }
}
