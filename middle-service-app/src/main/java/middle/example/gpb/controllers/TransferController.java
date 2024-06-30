package middle.example.gpb.controllers;

import jakarta.validation.Valid;
import middle.example.gpb.models.CreateTransferRequest;
import middle.example.gpb.models.ResponseToFront;
import middle.example.gpb.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/transfer")
public class TransferController {

    private final TransferService transferService;

    @Autowired
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<ResponseToFront> transferAmount(@Valid @RequestBody CreateTransferRequest transferRequest) {
        var response = transferService.transferMoney(transferRequest) ?
                new ResponseToFront("Перевод успешно совершен!") :
                new ResponseToFront("Сумма для перевода введенная вами некорректна, пожалуйста, измените сумму и попробуйте снова.");
        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }
}
