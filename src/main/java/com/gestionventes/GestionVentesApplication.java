package com.gestionventes;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GestionVentesApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(GestionVentesApplication.class, args);
        System.out.println("Application démarrée sur http://localhost:8081");
    }
    
    @GetMapping("/api/test")
    public String test() {
        return "Spring Boot + Oracle API fonctionne!";
    }
    
    @GetMapping("/api/health")
    public Map<String, String> health() {
        return Map.of(
            "status", "OK",
            "service", "Gestion Ventes API",
            "timestamp", LocalDateTime.now().toString(),
            "database", "Oracle XE"
        );
    }
}