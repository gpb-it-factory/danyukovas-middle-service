package middle.example.gpb.controllers;

import jakarta.validation.Valid;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> registerNewUser(@Valid @RequestBody CreateUserRequestV2 newUser) {
        String result = userService.responseFromBackend(newUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }
}
