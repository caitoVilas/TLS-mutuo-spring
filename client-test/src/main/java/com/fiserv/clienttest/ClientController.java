package com.fiserv.clienttest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final WebClient webClient;

    @GetMapping("/test")
    public String test() {
        return webClient.get()
                .uri("https://localhost:50443/server/test")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
