package controller;

import usecase.registration.api.RegistrationApplier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final RegistrationApplier registrationApplier;

    public RegistrationController(RegistrationApplier registrationApplier) {
        this.registrationApplier = registrationApplier;
    }
}

