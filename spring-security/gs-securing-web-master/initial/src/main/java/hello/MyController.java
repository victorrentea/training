package hello;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class MyController {
    @Value("${a}")
    private String a;

    @GetMapping("/transfer")
    @ResponseBody
    public String transferMoney(@RequestParam String accountNo, @RequestParam String amount) {
        log.debug("Transferring EUR {} to account {}", amount, accountNo);
        return "CAT!";
    }

    @GetMapping("redirect")
    public String redirect() {
        return "redirect:https://myhost.com/some/arbitrary/path";
    }
    @GetMapping("locale")
    @ResponseBody
    public String locale(HttpServletRequest request) {
        return new RequestContext(request).getLocale().toString();
    }
}
