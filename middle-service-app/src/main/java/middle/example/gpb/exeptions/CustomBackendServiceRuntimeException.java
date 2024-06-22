package middle.example.gpb.exeptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import middle.example.gpb.models.InnerErrorV2;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;

@Getter
public class CustomBackendServiceRuntimeException extends RuntimeException {

    private final InnerErrorV2 error;

    @Autowired
    @SneakyThrows
    public CustomBackendServiceRuntimeException(String message, InputStream body, ObjectMapper mapper) {
        super(message);
        error = mapper.readValue(body, InnerErrorV2.class);
    }
}
