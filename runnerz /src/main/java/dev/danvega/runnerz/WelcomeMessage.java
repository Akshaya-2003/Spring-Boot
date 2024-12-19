package dev.danvega.runnerz;

import org.springframework.stereotype.Component;

@Component


public class WelcomeMessage {
    public String getMessage() {
        return "Welcome to the application!";
    }
}
