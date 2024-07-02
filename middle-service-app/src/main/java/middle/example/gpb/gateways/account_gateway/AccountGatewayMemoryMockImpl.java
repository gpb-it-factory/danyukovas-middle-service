package middle.example.gpb.gateways.account_gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import middle.example.gpb.exeptions.CustomBackendServiceRuntimeException;
import middle.example.gpb.gateways.BackendRepositoryMock;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.models.InnerErrorV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "features", name = "backendServiceEnabled", havingValue = "false", matchIfMissing = true)
public class AccountGatewayMemoryMockImpl implements AccountGateway {

    private final BackendRepositoryMock repMock;
    private final ObjectMapper mapper;

    @Autowired
    public AccountGatewayMemoryMockImpl(BackendRepositoryMock repMock, ObjectMapper mapper) {
        this.repMock = repMock;
        this.mapper = mapper;
    }

    @Override
    public void newAccountRegisterResponse(CreateAccountRequestV2 accountRequest, long id) {
        if (repMock.getRepository().get(id).isEmpty()) {
            log.debug("Добавление аккаунта пользователя {} в систему.", id);
            repMock.getRepository().put(id,
                    new ArrayList<>(List.of(new AccountsListResponseV2(UUID.randomUUID(), accountRequest.accountName(), new BigDecimal(5000)))));
        } else {
            try {
                byte[] error = mapper.writeValueAsBytes(
                        new InnerErrorV2("Аккаунт уже существует.", "Error", "409", UUID.randomUUID()));
                throw new CustomBackendServiceRuntimeException("message", new ByteArrayInputStream(error), mapper);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<AccountsListResponseV2> allAccountsResponse(long id) {
        log.debug("Проверка на наличие аккаунтов у пользователя {}.", id);
        var accountsResponse = repMock.getRepository().get(id);
        if (accountsResponse.isEmpty()) {
            try {
                byte[] error = mapper.writeValueAsBytes(
                        new InnerErrorV2("Нет созданных аккаунтов.", "Error", "404", UUID.randomUUID()));
                throw new CustomBackendServiceRuntimeException("message", new ByteArrayInputStream(error), mapper);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.debug("Получение аккаунтов пользователя {}.", id);
            return accountsResponse;
        }
    }
}
