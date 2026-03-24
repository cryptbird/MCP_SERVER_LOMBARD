package com.icicilombard.mcp.service;

import com.icicilombard.mcp.config.AppProperties;
import com.icicilombard.mcp.model.Dtos.CreateTokenResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AuthTokenService {

    private static final DateTimeFormatter EXPIRY_FMT = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
    private final RestClient restClient;
    private final AppProperties appProperties;

    private String cachedToken;
    private Instant expiresAt = Instant.MIN;

    public AuthTokenService(RestClient restClient, AppProperties appProperties) {
        this.restClient = restClient;
        this.appProperties = appProperties;
    }

    public synchronized String getValidToken() {
        if (cachedToken != null && Instant.now().isBefore(expiresAt.minusSeconds(60))) {
            return cachedToken;
        }
        CreateTokenResponse response = restClient.post()
                .uri("/CreateToken")
                .header("Username", appProperties.getIltc().getUsername())
                .header("Password", appProperties.getIltc().getPassword())
                .retrieve()
                .body(CreateTokenResponse.class);
        if (response == null || response.data() == null || response.data().accessToken() == null) {
            throw new IllegalStateException("Unable to create auth token");
        }
        cachedToken = response.data().accessToken();
        LocalDateTime ldt = LocalDateTime.parse(response.data().expiryTime(), EXPIRY_FMT);
        expiresAt = ldt.atZone(ZoneId.systemDefault()).toInstant();
        return cachedToken;
    }
}
