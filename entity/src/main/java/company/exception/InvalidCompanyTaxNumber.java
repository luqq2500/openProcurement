package company.exception;

public class InvalidCompanyTaxNumber extends RuntimeException {
    public InvalidCompanyTaxNumber(String s) {
        super(s);
    }
}
