package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class MyController {
    @Value("${a}")
    private String a;

    @GetMapping("/transfer")
    public String transferMoney(@RequestParam String accountNo, @RequestParam String amount) {
        log.debug("Transferring EUR {} to account {}", amount, accountNo);
        return "CAT!";
    }
}
