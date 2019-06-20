package hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PeacefulController {

    @GetMapping("/rest")
    public String rest() {
        return "Peace on you";
    }
}
