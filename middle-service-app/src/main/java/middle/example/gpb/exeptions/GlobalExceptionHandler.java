package middle.example.gpb.exeptions;

import middle.example.gpb.models.InnerError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new InnerError("Отсутствует тело пользователя", "UserError", "123", UUID.randomUUID()));
    }
}
