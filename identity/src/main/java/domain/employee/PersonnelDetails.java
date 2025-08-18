package domain.employee;

public class PersonnelDetails {
    private String firstName;
    private String lastName;
    private String email;
    public PersonnelDetails(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    public void changeEmail(String email) {
        this.email = email;
    }
}
