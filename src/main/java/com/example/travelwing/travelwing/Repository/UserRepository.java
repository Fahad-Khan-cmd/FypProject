package com.example.travelwing.travelwing.Repository;

import com.example.travelwing.travelwing.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
//    User findByUserName();
}
