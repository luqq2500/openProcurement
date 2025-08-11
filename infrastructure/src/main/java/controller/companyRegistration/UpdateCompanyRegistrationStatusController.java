package controller.companyRegistration;

import administrator.UpdateCompanyRegistrationStatusRequest;
import administrator.UpdateCompanyRegistrationStatusResponse;
import administrator.api.CompanyRegistrationStatusUpdater;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/registrations")
public class UpdateCompanyRegistrationStatusController {
    private final CompanyRegistrationStatusUpdater statusUpdater;

    public UpdateCompanyRegistrationStatusController(CompanyRegistrationStatusUpdater statusUpdater) {
        this.statusUpdater = statusUpdater;
    }

    @PostMapping()
    public ResponseEntity<UpdateCompanyRegistrationStatusResponse> updateCompanyRegistrationStatus(@RequestBody @Valid UpdateCompanyRegistrationStatusRequest request) {
        UpdateCompanyRegistrationStatusResponse response = statusUpdater.update(request);
        return ResponseEntity.ok(response);
    }
}
