package company.exception;

public class CompanyAlreadyExistException extends RuntimeException  {
    public CompanyAlreadyExistException(String message) {
        super(message);
    }
}
