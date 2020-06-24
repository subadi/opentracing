package opentracinng;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

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
public class DemoController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Tracer tracer;

/*    @Autowired
    User user;*/

    @GetMapping("/subscribeAllServices/{amount}/{flag}")
    public String sayHello(@PathVariable int amount,@PathVariable boolean flag) {
        Span span = tracer.activeSpan();
        User user = new User();

        int newBalance= addBalance(user,amount);

        user = subscribeMoviePackage(newBalance);
        System.out.println("Responce received from subs: "+user.getEmail()+" "+user.getBalance());

        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put("Uemail", user.getEmail());
        fields.put("Ubalance", user.getBalance());
        span.log(fields);

        user = subscribeSpecialpackage(flag);
        System.out.println("Responce received from Specialsubs: "+user.getEmail()+" "+user.getBalance());


        span.setTag("Final Respose:",user.toString());
        user.setEmail("Useremail@somexample.com");
        user.setBalance(500);
        user.setisSpecialSubscribed(true);
        user.setpack("Gold Pack");

        String response = "User Email:"+user.getEmail()+" User Balance: "+user.getBalance()+" User Movie Pack Service"+user.getpack();
        return response;
    }

    private int addBalance(User user,int amt) {
        System.out.println("user default amt is: "+amt);
        int newamt= user.getBalance()+amt;
        System.out.println("new amount: "+newamt);
        return newamt;
    }

    private User subscribeMoviePackage(int balance) {
        String url = "http://localhost:8091/subscribeMovie/" + balance;
        ResponseEntity<User> response = restTemplate.getForEntity(url,User.class);
        System.out.println("response from subscribe service "+response.getBody());
        return response.getBody();
    }

    private User subscribeSpecialpackage(boolean flag) {
        URI uri = UriComponentsBuilder //
            .fromHttpUrl("http://localhost:8092/subscribeSpecial") //
            .queryParam("flag",flag)
            .build(Collections.emptyMap());
        ResponseEntity<User> response = restTemplate.getForEntity(uri, User.class);
        return response.getBody();
    }
}
