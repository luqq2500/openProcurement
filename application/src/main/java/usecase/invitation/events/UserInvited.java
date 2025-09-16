package usecase.invitation.events;

import domain.invitation.UserInvitation;
import event.DomainEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class UserInvited implements DomainEvent {
    private final UUID invitationId;
    private final UUID accountId;
    private final String email;
    private final Instant timestamp;

    public UserInvited(UserInvitation userInvitation) {
        this.invitationId = userInvitation.invitationId();
        this.accountId = userInvitation.accountId();
        this.email = userInvitation.invitationEmail();
        this.timestamp = Instant.now();
    }
    @Override
    public Instant getTimestamp() {
        return timestamp;
    }
    public UUID getInvitationId() {
        return invitationId;
    }
    public UUID getAccountId() {
        return accountId;
    }

    public String getEmail() {
        return email;
    }
}
