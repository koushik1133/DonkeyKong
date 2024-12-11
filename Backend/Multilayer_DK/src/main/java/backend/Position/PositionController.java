package backend.Position;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PositionController {

    @GetMapping("/status")
    public String getStatus() {
        return "WebSocket server is running!";
    }
}
