package applicant;

import java.util.Objects;

public record Applicant(String firstName, String lastName, String email, String phoneNumber) {
    public Applicant{
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(email);
        Objects.requireNonNull(phoneNumber);
    }
}
