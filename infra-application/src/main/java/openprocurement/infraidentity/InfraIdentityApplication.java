package openprocurement.infraidentity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("ddd")
@SpringBootApplication
public class InfraIdentityApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfraIdentityApplication.class, args);
    }

}
