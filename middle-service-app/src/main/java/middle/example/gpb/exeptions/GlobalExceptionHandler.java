package middle.example.gpb.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Полученные данные не валидны, пожалуйста введите верную информацию.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleHttpRuntimeException() {
        return ResponseEntity.status(HttpStatus.OK)
                .body("Что-то пошло не так.");
    }
}
