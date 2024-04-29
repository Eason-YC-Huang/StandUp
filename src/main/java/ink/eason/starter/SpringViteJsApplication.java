package ink.eason.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@SpringBootApplication
public class SpringViteJsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringViteJsApplication.class, args);
    }

    @GetMapping("/greeting")
    public String greet() {
        return "Hello, Spring Vite JS";
    }

}
