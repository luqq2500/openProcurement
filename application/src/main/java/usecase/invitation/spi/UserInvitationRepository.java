package usecase.invitation.spi;

import domain.invitation.UserInvitation;

public interface UserInvitationRepository {
    void add(UserInvitation userInvitation);
}
