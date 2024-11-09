package backend.Countdown;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countdown")
public class CountdownController {

    private final CountdownHandler countdownHandler;

    @Autowired
    public CountdownController(CountdownHandler countdownHandler) {
        this.countdownHandler = countdownHandler;
    }

    @PostMapping("/start")
    public String startCountdown(@RequestParam(defaultValue = "12") int durationInSeconds) {
        countdownHandler.startCountdown(durationInSeconds);
        return "Countdown started for " + durationInSeconds + " seconds.";
    }
}
