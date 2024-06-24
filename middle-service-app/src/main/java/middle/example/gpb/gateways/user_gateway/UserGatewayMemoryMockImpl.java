package middle.example.gpb.gateways.user_gateway;

import middle.example.gpb.models.CreateUserRequestV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "false", matchIfMissing = true)
public class UserGatewayMemoryMockImpl implements UserGateway {

    private final UserBackendRepMock repository;

    @Autowired
    public UserGatewayMemoryMockImpl(UserBackendRepMock repository) {
        this.repository = repository;
    }

    @Override
    public boolean newUserRegisterResponse(CreateUserRequestV2 userRequest) {
        List<Long> users = repository.getUsers();
        if (users.contains(userRequest.userId())) {
            return false;
        } else {
            users.add(userRequest.userId());
            return true;
        }
    }
}
