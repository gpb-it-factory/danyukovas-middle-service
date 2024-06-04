package middle.example.gpb.controllers;

import jakarta.websocket.server.PathParam;
import middle.example.gpb.config.FeatureToggleConfig;
import middle.example.gpb.models.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/users")
public class UsersController {

    private final FeatureToggleConfig featureConfig;

    @Autowired
    public UsersController(FeatureToggleConfig featureConfig) {
        this.featureConfig = featureConfig;
    }

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody CreateUserRequest newUser) {
        if (featureConfig.isBackendServiceEnabled()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .build();
        }
    }
}
