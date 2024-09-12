package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcomeMessage() {
        return """
                Hello, this is the main page! Here are some extensions:
                /createPerson
                /updatePerson/{name}
                /deletePerson/{name}
                /getPerson/{name}
                /all
                """;
    }
}
