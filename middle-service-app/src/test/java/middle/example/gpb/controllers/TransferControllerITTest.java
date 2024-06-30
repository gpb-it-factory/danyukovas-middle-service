package middle.example.gpb.controllers;

import middle.example.gpb.models.ResponseToFront;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class TransferControllerITTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenValidDataAndSuccessTransferTest() throws Exception {

        var exp = new ResponseToFront("Перевод успешно совершен!");

        mockMvc.perform(post("/api/v2/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "from": "6",
                                    "to": "test7",
                                    "amount": 1000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(exp.answer()));
    }

    @Test
    public void whenValidDataAndNotEnoughMoneyTransferTest() throws Exception {

        var exp = new ResponseToFront("Сумма для перевода введенная вами некорректна, пожалуйста, измените сумму и попробуйте снова.");

        mockMvc.perform(post("/api/v2/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "from": "6",
                                    "to": "test7",
                                    "amount": 10000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(exp.answer()));
    }

    @Test
    public void whenTransferAmountNotFoundUserToAccountTest() throws Exception {

        var exp = new ResponseToFront("Нет созданных аккаунтов.");

        mockMvc.perform(post("/api/v2/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "from": "6",
                                    "to": "test1234",
                                    "amount": 2300
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer").value(exp.answer()));
    }
}

