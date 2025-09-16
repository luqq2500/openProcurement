package usecase.invitation.events;

import domain.invitation.UserInvitation;
import event.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public class UserInvitedIE implements IntegrationEvent {
    private final UUID eventId;
    private final UUID accountId;
    private final String email;
    private final Instant timestamp;

    public UserInvitedIE(UUID accountId, String email) {
        this.eventId = UUID.randomUUID();
        this.accountId = accountId;
        this.email = email;
        this.timestamp = Instant.now();
    }


    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public String getEventType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }
}
