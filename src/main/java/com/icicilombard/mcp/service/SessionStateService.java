package com.icicilombard.mcp.service;

import com.icicilombard.mcp.model.SessionState;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class SessionStateService {

    private final ConcurrentHashMap<String, SessionState> states = new ConcurrentHashMap<>();

    public SessionState get(String sessionId) {
        return states.computeIfAbsent(sessionId, ignored -> new SessionState());
    }
}
