package com.social.gateway.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("refresh_tokens")
public class RefreshToken {
    @Id
    private Long id;
    private String token;
    private Long userId;
    private LocalDateTime expiryDate;

    public RefreshToken() {}

    public RefreshToken(String token, Long userId, LocalDateTime expiryDate) {
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public LocalDateTime getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDateTime expiryDate) { this.expiryDate = expiryDate; }
}