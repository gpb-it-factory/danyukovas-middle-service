package middle.example.gpb.controllers;

import jakarta.validation.Valid;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.models.ResponseToFront;
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
    public ResponseEntity<ResponseToFront> registerNewUser(@Valid @RequestBody CreateUserRequestV2 newUser) {
        boolean isSuccessRegistration = userService.responseFromBackend(newUser);
        if (isSuccessRegistration) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseToFront("Пользователь успешно зарегистрирован."));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("Такой пользователь уже создан."));
    }
}
