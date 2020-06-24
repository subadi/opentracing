package opentracinng.frontendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FrontEndApp {

    public static void main(String[] args) {
        System.setProperty("server.port", "8095");
        SpringApplication.run(FrontEndApp.class, args);
    }
}
