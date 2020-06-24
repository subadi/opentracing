package opentracinng.subscription;

import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import model.user.User;
import opentracinng.TracedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController extends TracedController {

    @GetMapping("/subscribeMovie/{balance}")
    public User subscribeMovie(@PathVariable int balance, HttpServletRequest request) {
        Span span = startServerSpan("/subscribeMovie", request);
        try (Scope scope = tracer.scopeManager().activate(span, false)) {
            User user = new User();

            Enumeration attr = request.getAttributeNames();
            while(attr.hasMoreElements())
            {
                System.out.println("Attr: "+attr.nextElement());
            }

            Map<String,String[]> requestMap= request.getParameterMap();

            for(int i=0;i<requestMap.size();i++){
            System.out.println("Request: "+requestMap.get(i));
        }

            if (balance <= 500) {
                //subscribe movie silver pack
                user.setpack("SILVER");
                user.setBalance(balance);
                user.setEmail("user.goldemail");
            } else if (balance > 500) {
                //subscribe movie gold pack
                user.setpack("GOLD");
                user.setBalance(balance);
                user.setEmail("user.silveremail");

            }
            Map<String, Object> fields = new LinkedHashMap<>();
            fields.put("Uemail", user.getEmail());
            fields.put("Ubalance", user.getBalance());
            System.out.println("Map is: " + fields);
            System.out.println("trace is: " + tracer);
            System.out.println("activespan is: " + tracer.activeSpan());

            span.log(fields);

            return user;
        } finally {
            span.finish();
        }
    }

}
