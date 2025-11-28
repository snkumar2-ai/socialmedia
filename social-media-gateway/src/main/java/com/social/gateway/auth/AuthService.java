package com.social.gateway.auth;

import com.social.gateway.security.JwtTokenProvider;
import com.social.gateway.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<Map<String, String>> login(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    String accessToken = jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
                    String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());
                    return Map.of(
                            "accessToken", accessToken,
                            "refreshToken", refreshToken,
                            "tokenType", "Bearer"
                    );
                });
    }

    public Mono<User> register(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword, Role.USER);
        return userRepository.save(user);
    }

    public Mono<Map<String, String>> refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            return Mono.empty();
        }
        
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        return userRepository.findByUsername(username)
                .map(user -> {
                    String newAccessToken = jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
                    return Map.of(
                            "accessToken", newAccessToken,
                            "tokenType", "Bearer"
                    );
                });
    }
}