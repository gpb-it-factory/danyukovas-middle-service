/*package middle.example.gpb.controllers;

import middle.example.gpb.models.CreateAccountRequestV2;
import middle.example.gpb.services.AccountService;
import middle.example.gpb.services.BackendServiceMock;
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
    public ResponseEntity<String> registerNewAccount(@PathVariable(name = "id") long id,
                                                @RequestBody CreateAccountRequestV2 newAccount) {
        String response = accountService;
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}*/
