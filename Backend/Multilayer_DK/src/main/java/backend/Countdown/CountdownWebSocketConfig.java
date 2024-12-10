package backend.Countdown;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class CountdownWebSocketConfig implements WebSocketConfigurer {

    @Bean
    public CountdownHandler countdownHandler() {
        return new CountdownHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(countdownHandler(), "/countdown")
                .setAllowedOrigins("*");
    }
}
