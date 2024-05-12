package com.example.travelwing.travelwing.Repository;

import com.example.travelwing.travelwing.Domain.UserPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferenceRepository extends JpaRepository<UserPreference,Long> {
}
