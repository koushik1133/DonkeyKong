package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello, welcome to the first page I launched with Spring Boot!";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello, your name is: " + name + "!";
    }
}
