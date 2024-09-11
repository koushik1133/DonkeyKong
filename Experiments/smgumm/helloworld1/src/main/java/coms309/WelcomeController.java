package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String intro() {
        return """
                Here is a list of available endpoints:
                /{name} to be greeted with a personalized message!
                /art to see some lovely ASCII art!""";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }

    @GetMapping("/art")
    public String art() {
        return """
                      _=====_                                _=====_
                     / _____ \\                             / _____ \\
                    +.-'_____'-.---------------------------.-'_____'-.+
                   /   |     |  '.        S O N Y        .'  |  _  |   \\
                  / ___| /|\\ |___ \\                   / ___| /_\\ |___ \\
                 / |      |      | ;  __           _   ; | _         _ | ;
                 | | <---   ---> | | |__|         |_:> | ||_|       (_)| |
                 | |___   |   ___| ;SELECT       START ; |___       ___| ;
                 |\\    | \\|/ |    /  _     ___      _   \\  | (X) |    /|
                 | \\   |_____|  .','" "', |___|  ,'" "', '.  |_____|  .' |
                 |  '-.______.-' /       \\ANALOG/       \\  '-._____.-'   |
                 |               |       |------|         |               |
                 |              /\\      /      \\       /\\              |
                 |             /  '.___.'        '.___.'  \\             |
                 |            /                            \\            |
                 \\          /                              \\          /
                  \\________/                                \\_________/
                                    PS2 CONTROLLER
                """;
    }
}
