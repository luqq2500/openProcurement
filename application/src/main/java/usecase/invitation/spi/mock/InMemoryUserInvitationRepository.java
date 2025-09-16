package usecase.invitation.spi.mock;

import domain.invitation.UserInvitation;
import usecase.invitation.spi.UserInvitationRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryUserInvitationRepository implements UserInvitationRepository {
    private final Map<UUID, UserInvitation> invitations = new HashMap<>();
    @Override
    public void add(UserInvitation userInvitation) {
        invitations.put(userInvitation.invitationId(), userInvitation);
    }
}
