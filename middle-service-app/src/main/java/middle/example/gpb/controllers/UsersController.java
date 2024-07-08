package middle.example.gpb.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.models.ResponseToFront;
import middle.example.gpb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
        userService.responseFromBackend(newUser);
        log.info("Отправка сообщения пользователю {} об успешной регистрации.", newUser.userId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("Пользователь успешно зарегистрирован."));
    }
}
