package victor.spring.play;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
public class Doi {
    @Bean
    public DoiA doiA() {
        return new DoiA(doiB());
    }
    @Bean
    public DoiA doiA2() {
        return new DoiA(doiB());
    }
    @Bean
    public DoiB doiB() {
        System.out.println("+2B");
        return new DoiB();
    }
}

@AllArgsConstructor
class DoiA {
    private final DoiB b;

}

class DoiB {
    @Cacheable("go")
    public String go() {
        System.out.println("In go");
        return "go";
    }

}
