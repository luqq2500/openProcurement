package openprocurement.identityinfra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"configuration"})
public class IdentityInfraApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdentityInfraApplication.class, args);
    }

}
