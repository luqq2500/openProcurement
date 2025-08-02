package applicant.dto;

import java.util.Objects;

public record RequestCompanyRegistrationCommand(String firstName, String lastName, String email, String phoneNumber) {
    public RequestCompanyRegistrationCommand {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(email);
        Objects.requireNonNull(phoneNumber);
    }
    public void validateEmailFormat(){
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            throw new IllegalArgumentException("Email "+ email + " format is invalid.");
        }
    }
}
