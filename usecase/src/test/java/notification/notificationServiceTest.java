package notification;

import notification.email.MockEmailService;
import org.junit.Test;

public class notificationServiceTest {

    @Test
    public void test() {
        NotificationService service = new MockEmailService();
        service.send(new NotificationMessage("hakimluqq25@gmail.com", "idk","message"));
    }
}
