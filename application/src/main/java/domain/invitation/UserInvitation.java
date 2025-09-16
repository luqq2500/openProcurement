package domain.invitation;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserInvitation (UUID invitationId, UUID accountId, String invitationEmail, LocalDateTime invitedAt) {
}
