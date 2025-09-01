package controller;

import usecase.registration.api.Registrator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final Registrator registrator;

    public RegistrationController(Registrator registrator) {
        this.registrator = registrator;
    }
}

