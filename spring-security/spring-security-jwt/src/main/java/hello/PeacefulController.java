package hello;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
public class PeacefulController {

    @Value("${jwt.secret}")
    private String jwtSecret;

    // in the resource server (your backend)
    @GetMapping("/rest")
    public String rest() {
        return "Peace on you";
    }

    @Autowired
    private RestTemplate restTemplate;

    // in the authorization server (e.g. a proxy?)
    @GetMapping("/")
    public String home(
            @RequestParam(defaultValue = "test") String user,
            @RequestParam(defaultValue = "LOW") String level
            ) throws URISyntaxException {

        AuthnContext authnContext = AuthnContext.valueOf(level);
        String jwtToken = Jwts.builder()
                .setSubject(user)
                .claim("AuthnContext", authnContext.name())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtAuthorizationHeaderFilter.JWT_HEADER_NAME, jwtToken);
        log.debug("JWT: " + jwtToken);

        RequestEntity<Object> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, new URI("http://localhost:8080/rest"));
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);


        return "Got: " + responseEntity.getBody();
        //some idea for propagating it over thread :https://stackoverflow.com/questions/46729203/propagate-http-header-jwt-token-over-services-using-spring-rest-template
    }
}
