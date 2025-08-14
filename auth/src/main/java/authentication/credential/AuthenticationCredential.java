package authentication.credential;

public abstract class AuthenticationCredential {
    abstract void verify(String secret);
}
