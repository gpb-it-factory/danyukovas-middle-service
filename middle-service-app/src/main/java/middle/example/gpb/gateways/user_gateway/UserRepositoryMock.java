package middle.example.gpb.gateways.user_gateway;

import lombok.Getter;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class UserRepositoryMock {

    List<Long> users = new ArrayList<>(List.of(1L, 2L, 3L, 4L, 5L));
}
