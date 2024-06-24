package middle.example.gpb.services;

import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.gateways.user_gateway.UserGateway;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserGateway userGateway;

    public UserService(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public String responseFromBackend(CreateUserRequestV2 createUserRequest) {
        return userGateway.newUserRegisterResponse(createUserRequest) ?
                "Пользователь успешно зарегистрирован." : "Такой пользователь уже создан.";
    }
}
