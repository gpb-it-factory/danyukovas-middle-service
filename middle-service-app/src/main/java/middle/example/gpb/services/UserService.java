package middle.example.gpb.services;

import lombok.extern.slf4j.Slf4j;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserGateway userGateway;

    public UserService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public void responseFromBackend(CreateUserRequestV2 createUserRequest) {
        log.info("Начало регистрации пользователя {} в системе.", createUserRequest.userId());
        userGateway.newUserRegisterResponse(createUserRequest);
    }
}
