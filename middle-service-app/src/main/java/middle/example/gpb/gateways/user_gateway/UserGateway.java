package middle.example.gpb.gateways.user_gateway;

import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.models.UserResponseV2;
import org.springframework.http.ResponseEntity;

public interface UserGateway {

    public boolean newUserRegisterResponse(CreateUserRequestV2 userRequest);

    public UserResponseV2 getUserResponse(long id);
}
