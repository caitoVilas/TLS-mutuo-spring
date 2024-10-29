package com.fiserv.inyector;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @autor caito Vilas   
 * Servidor que acepta conecciones SSL/TLS
 * prueba de comunicacion mutual TLS con intercambio de certificados PKCS12
 * JAVA 17 con Spring Boot 3.3.4
 */
@RestController
@RequestMapping("/server")
public class ServerController {

    @GetMapping("/test")
    private String test() {
        return "¡Conexión segura establecida!";
    }
}
