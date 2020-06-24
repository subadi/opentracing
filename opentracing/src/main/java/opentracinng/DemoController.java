package opentracinng;

import io.opentracing.Scope;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import io.opentracing.Span;
import io.opentracing.Tracer;
import model.user.User;


@RestController
public class DemoController extends TracedController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/subscribeAllServices/{amount}/{flag}")
    public String subscribeAll(@PathVariable int amount, @PathVariable boolean flag,
        HttpServletRequest request) {
        Span span = startServerSpan("/subscribeAll", request);
        try (Scope scope = tracer.scopeManager().activate(span, false)) {
            User user = new User();

            int newBalance = addBalance(user, amount);
            user = subscribeMoviePackage(newBalance);

            System.out.println(
                "Responce received from subs: " + user.getEmail() + " " + user.getBalance());

            Map<String, Object> fields = new LinkedHashMap<>();
            fields.put("Uemail", user.getEmail());
            fields.put("Ubalance", user.getBalance());
            span.log(fields);

            user = subscribeSpecialpackage(flag);
            System.out.println(
                "Responce received from Specialsubs: " + user.getEmail() + " " + user.getBalance());

            span.setTag("Final Respose:", user.toString());
            user.setEmail("Useremail@somexample.com");
            user.setBalance(500);
            user.setisSpecialSubscribed(true);
            user.setpack("Gold Pack");

            String response =
                "User Email:" + user.getEmail() + " User Balance: " + user.getBalance()
                    + " User Movie Pack Service" + user.getpack();
            return response;
        } finally {
            span.finish();
        }
    }

    private int addBalance(User user, int amt) {
        System.out.println("user default amt is: " + amt);
        int newamt = user.getBalance() + amt;
        System.out.println("new amount: " + newamt);
        return newamt;
    }

    private User subscribeMoviePackage(int balance) {
        String url = "http://localhost:8091/subscribeMovie/" + balance;
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build(Collections.emptyMap());

        //another way to call rest method
/*        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
        return response.getBody();*/

        return get("subscribe-movie", uri, User.class, restTemplate);
    }

    private User subscribeSpecialpackage(boolean flag) {
        URI uri = UriComponentsBuilder //
            .fromHttpUrl("http://localhost:8092/subscribeSpecial") //
            .queryParam("flag", flag)
            .build(Collections.emptyMap());

        return get("subscribe-special", uri, User.class, restTemplate);
    }
}
