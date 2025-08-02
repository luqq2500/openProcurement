package applicant;

public record Applicant(String firstName, String lastName, String email, String phoneNumber) {
    public void validateEmailEqualsTo(String email) {
        if (!this.email.matches(email)) {
            throw new RuntimeException("Invalid email");
        }
    }
}
