package middle.example.gpb.controllers;

import jakarta.validation.Valid;
import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.models.ResponseToFront;
import middle.example.gpb.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        boolean isSuccessCreation = accountService.createNewAccount(newAccount, id);
        if (isSuccessCreation) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseToFront("Аккаунт успешно создан."));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseToFront("Пользователь не найден. Пожалуйста, сначала выполните регистрацию."));
    }
}
