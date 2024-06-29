package middle.example.gpb.exeptions;

import middle.example.gpb.models.ResponseToFront;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseToFront> handleRuntimeException() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("Что-то пошло не так."));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ResponseToFront> handleResourceAccessException() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("""
                        К сожалению, Backend сервис сейчас недоступен, нам очень жаль, попробуйте позже. Вот вам зайчик:\s
                        (\\(\\
                        ( -.-)
                        o_(")(")"""));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResponseToFront> handleNoResourceFoundException() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("К сожалению, запроса по вашей команде не существует, сообщите о проблеме тех. поддержке."));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseToFront> handleHttpMessageNotReadableException() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("Задан пустой запрос, пожалуйста, введите данные."));
    }

    @ExceptionHandler(CustomBackendServiceRuntimeException.class)
    public ResponseEntity<ResponseToFront> handleMyCustomRuntimeException(CustomBackendServiceRuntimeException e) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront(e.getError().message()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseToFront> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String invalidDataInString = validatorBuilder(ex);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("Полученные данные не валидны, пожалуйста, введите верную информацию." + "\n"
                + "Невалидные данные:" + "\n" + invalidDataInString));
    }

    private String validatorBuilder(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(v -> (FieldError) v)
                .map(v -> "Поле " + v.getField() + " " + v.getDefaultMessage() + ".")
                .collect(Collectors.joining("\n"));
    }
}
