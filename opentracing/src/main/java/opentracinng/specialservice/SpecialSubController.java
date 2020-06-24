package opentracinng.specialservice;


import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.Tracer;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import model.user.User;
import opentracinng.TracedController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpecialSubController extends TracedController {

    @GetMapping("/subscribeSpecial")
    public User subscribeSpecialService(User user,@RequestParam boolean flag,
        HttpServletRequest request) {
        Span span = startServerSpan("/subscribeSpecial", request);
        try (Scope scope = tracer.scopeManager().activate(span, false)) {
            if (flag) {
                user.setisSpecialSubscribed(true);
            } else {
                user.setisSpecialSubscribed(false);
            }

            Map<String, Object> fields = new LinkedHashMap<>();
            fields.put("Uemail", user.getEmail());
            fields.put("Ubalance", user.getBalance());
            fields.put("SpecialService", user.isSpecialSubscribed());
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
