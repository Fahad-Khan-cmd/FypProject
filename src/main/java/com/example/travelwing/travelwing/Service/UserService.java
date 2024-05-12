package com.example.travelwing.travelwing.Service;

import com.example.travelwing.travelwing.Domain.User;
import com.example.travelwing.travelwing.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void insertUsers(User user)
    {
        userRepository.save(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }


}
