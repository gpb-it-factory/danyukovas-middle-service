package middle.example.gpb.gateways.user_gateway;

import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.models.UserResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "false", matchIfMissing = true)
public class UserGatewayMemoryMockImpl implements UserGateway {

    private final BackendRepositoryMock backendRepository;

    @Autowired
    public UserGatewayMemoryMockImpl(BackendRepositoryMock backendRepository) {
        this.backendRepository = backendRepository;
    }

    @Override
    public boolean newUserRegisterResponse(CreateUserRequestV2 userRequest) {
        Set<Long> users = backendRepository.getRepository().keySet();
        if (users.contains(userRequest.userId())) {
            return false;
        } else {
            backendRepository.getRepository().put(userRequest.userId(), new HashMap<>());
            return true;
        }
    }

    @Override
    public UserResponseV2 getUserResponse(long id) {
        if (backendRepository.getRepository().containsKey(id)) {
            return new UserResponseV2(UUID.randomUUID());
        };
        return null;
    }
}
