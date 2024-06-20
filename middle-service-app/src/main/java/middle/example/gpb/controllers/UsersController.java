package middle.example.gpb.controllers;

import middle.example.gpb.config.FeatureToggleConfig;
import middle.example.gpb.models.CreateUserRequestV2;
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
    public ResponseEntity<String> registerNewUser(@RequestBody CreateUserRequestV2 newUser) {
        if (featureConfig.isBackendServiceEnabled()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("""
                            На данный момент Backend сервиса нет, вот вам зайчик:\s
                            (\\(\\
                            ( -.-)
                            o_(")(")""");
        }
    }
}
