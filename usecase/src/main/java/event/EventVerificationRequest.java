package event;

import ddd.DomainEvent;
import notification.NotificationType;

public record EventVerificationRequest(String from) {
}
