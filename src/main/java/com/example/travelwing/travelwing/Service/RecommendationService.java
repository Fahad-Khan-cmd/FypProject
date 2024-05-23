package com.example.travelwing.travelwing.Service;

import com.example.travelwing.travelwing.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RuleEngine ruleEngine;
    public List<String> getRecommendations(User user) {
        return ruleEngine.getRecommendations(user);
    }

}
