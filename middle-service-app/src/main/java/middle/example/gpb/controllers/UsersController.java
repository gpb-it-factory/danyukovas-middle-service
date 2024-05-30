package middle.example.gpb.controllers;

import jakarta.websocket.server.PathParam;
import middle.example.gpb.models.CreateUserRequest;
import middle.example.gpb.models.InnerError;
import middle.example.gpb.models.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/users")
public class UsersController {

    @PostMapping
    public ResponseEntity<?> registerNewUser(@RequestBody(required = false) CreateUserRequest newUser) {
        //TODO отправить данные в BACKEND.
        if (newUser == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new InnerError("Отсутствует тело пользователя", "UserError", "123", UUID.randomUUID()));
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
/*
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        //TODO получить данные из BACKEND в UserResponse.
        return ResponseEntity.status(HttpStatus.OK)
                .body(new UserResponse(UUID.randomUUID()));
    }*/




}
