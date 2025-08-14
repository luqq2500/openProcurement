package user.spi;

import user.User;

import java.util.UUID;

public interface UserRepository {
    User getByEmail(String email);
    void add(User user);
}
