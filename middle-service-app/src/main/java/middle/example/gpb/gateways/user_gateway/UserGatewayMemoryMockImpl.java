package middle.example.gpb.gateways.user_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.CreateUserRequestV2;
import middle.example.gpb.models.InnerErrorV2;
import middle.example.gpb.models.UserResponseV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "false", matchIfMissing = true)
public class UserGatewayMemoryMockImpl implements UserGateway {

    private final BackendRepositoryMock backendRepository;
    private final ObjectMapper mapper;

    @Autowired
    public UserGatewayMemoryMockImpl(BackendRepositoryMock backendRepository, ObjectMapper mapper) {
        this.backendRepository = backendRepository;
        this.mapper = mapper;
    }

    @Override
    public void newUserRegisterResponse(CreateUserRequestV2 userRequest) {
        Set<Long> users = backendRepository.getRepository().keySet();
        if (!users.contains(userRequest.userId())) {
            backendRepository.getRepository().put(userRequest.userId(), new ArrayList<>());
            backendRepository.getUsers().put(userRequest.userId(), userRequest.userName());
        } else {
            try {
                byte[] error = mapper.writeValueAsBytes(
                        new InnerErrorV2("Такой пользователь уже зарегистрирован.", "Error", "409", UUID.randomUUID()));
                throw new CustomBackendServiceRuntimeException("message", new ByteArrayInputStream(error), mapper);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public UserResponseV2 getUserResponse(long id) {
        if (backendRepository.getRepository().containsKey(id)) {
            return new UserResponseV2(UUID.randomUUID());
        } else {
            try {
                byte[] error = mapper.writeValueAsBytes(
                        new InnerErrorV2("Пользователь не найден. Пожалуйста, сначала выполните регистрацию.", "Error", "404", UUID.randomUUID()));
                throw new CustomBackendServiceRuntimeException("message", new ByteArrayInputStream(error), mapper);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
