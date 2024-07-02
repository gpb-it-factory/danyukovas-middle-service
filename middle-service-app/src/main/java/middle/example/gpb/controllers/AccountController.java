package middle.example.gpb.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.models.ResponseToFront;
import middle.example.gpb.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v2/users/{id}/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<ResponseToFront> registerNewAccount(@PathVariable(name = "id") long id,
                                                              @Valid @RequestBody CreateAccountRequestV2 newAccount) {
        accountService.createNewAccount(newAccount, id);
        log.info("Отправка пользователю {} сообщения об успешной регистрации аккаунта.", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("Аккаунт успешно создан."));
    }

    @GetMapping
    public ResponseEntity<ResponseToFront> getAccountBalance(@PathVariable(name = "id") long id) {
        List<AccountsListResponseV2> response = accountService.getAllAccounts(id);
        log.info("Отправка сообщения пользователю {} всех имеющихся аккаунтов.", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront(response.stream()
                        .map(AccountsListResponseV2::toString)
                        .collect(Collectors.joining("\n"))));
    }
}
