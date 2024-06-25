package middle.example.gpb.controllers;

import jakarta.validation.Valid;
import middle.example.gpb.models.AccountsListResponseV2;
import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v2/users/{id}/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<String> registerNewAccount(@PathVariable(name = "id") long id,
                                                @Valid @RequestBody CreateAccountRequestV2 newAccount) {
        String response = accountService.createNewAccount(newAccount, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<String> getAccountBalance(@PathVariable(name = "id") long id) {
        String response = accountService.getAllAccounts(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
