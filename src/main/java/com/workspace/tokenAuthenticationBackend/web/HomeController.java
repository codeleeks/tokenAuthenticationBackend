package com.workspace.tokenAuthenticationBackend.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class HomeController {
    @GetMapping("/")
    public void home() {
        log.info("home page");
//        String token = new JWTGenerator().makeJwtToken();
//        log.info(token);
    }

    Map<String, List<String>> items = Map.of(
            "codeleeks", List.of("authorized item1", "authorized item2")
    );

    @GetMapping("/items")
    public List<String> someAuthUrl(@RequestParam String email) {
        log.info(email);
        log.info(items.get("codeleeks").toString());
        log.info(items.get(email).toString());
        return items.get(email);
    }

    @GetMapping("/test")
    public List<String> test(@RequestParam String email) {
                return items.get(email);
    }
}
