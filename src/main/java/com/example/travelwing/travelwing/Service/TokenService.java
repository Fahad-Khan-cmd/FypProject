package com.example.travelwing.travelwing.Service;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {
    private final Set<String> blacklistedTokens = new HashSet<>();
//

    public void invalidateToken(String token) {
        blacklistedTokens.add(token);
    }

    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public String extractTokenFromUserDetails(UserDetails userDetails){

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        }
        return null;
    }


}
