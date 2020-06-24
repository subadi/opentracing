package opentracinng.subscription;

import io.opentracing.Span;
import io.opentracing.Tracer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {

/*    @Autowired
    private PersonRepository personRepository;*/

    @Autowired
    private Tracer tracer;

    @GetMapping("/subscribeMovie/{balance}")
    public User getPerson(@PathVariable int balance) {
        Span span = tracer.activeSpan();
        User user = new User();

        if(balance<=500)
        {
            //subscribe movie silver pack
            user.setpack("SILVER");
            user.setBalance(balance);
            user.setEmail("user.goldemail");
        }
        else if(balance>500)
        {
            //subscribe movie gold pack
            user.setpack("GOLD");
            user.setBalance(balance);
            user.setEmail("user.silveremail");

        }
        Map<String, Object> fields = new LinkedHashMap<>();
        fields.put("Uemail", user.getEmail());
        fields.put("Ubalance", user.getBalance());
        System.out.println("Map is: "+fields);
        System.out.println("trace is: "+tracer);
        System.out.println("activespan is: "+tracer.activeSpan());

        span.log(fields);
        return user;
    }
}
