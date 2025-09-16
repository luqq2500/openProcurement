package usecase.invitation.api;

import java.util.UUID;

public interface UserInviter {
    void invite(UUID accountId, String email);
}
