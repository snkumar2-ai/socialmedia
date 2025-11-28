package com.social.gateway.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        
        return authService.login(username, password)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<User>> register(@RequestBody Map<String, String> registerRequest) {
        String username = registerRequest.get("username");
        String email = registerRequest.get("email");
        String password = registerRequest.get("password");
        
        return authService.register(username, email, password)
                .map(ResponseEntity::ok)
                .onErrorReturn(ResponseEntity.badRequest().build());
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<Map<String, String>>> refresh(@RequestBody Map<String, String> refreshRequest) {
        String refreshToken = refreshRequest.get("refreshToken");
        
        return authService.refreshToken(refreshToken)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}