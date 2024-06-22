package middle.example.gpb.exeptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleMethodArgumentNotValidException() {

        List<ObjectError> fieldErrorList = List.of(
                new FieldError("createUserRequestV2", "userId", "not null")
        );
        var bindingResult = mock(BindingResult.class);
        var exception = new MethodArgumentNotValidException(null, bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(fieldErrorList);

        var res = globalExceptionHandler.handleMethodArgumentNotValidException(exception).getBody();
        var exp = """
                Полученные данные не валидны, пожалуйста, введите верную информацию.
                Невалидные данные:
                Поле userId not null.""";

        assertEquals(exp, res);
    }

    @Test
    void testHandleMyCustomRuntimeException() throws IOException {

        var mapper = new ObjectMapper();
        var data = Files.newInputStream(Paths.get("src/test/test-data/error.json"));
        var exception = new CustomBackendServiceRuntimeException("mock", data, mapper);

        String res = globalExceptionHandler.handleMyCustomRuntimeException(exception).getBody();
        String exp = "result test message";

        assertEquals(exp, res);
    }
}
