package com.ikernell.backend.service;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
public class TokenService {
    public String generarToken() {
        return UUID.randomUUID().toString();
    }
}
