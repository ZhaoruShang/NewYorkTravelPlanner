package com.flag.travelplanner.user.service;

import com.flag.travelplanner.user.repository.UserRepository;
import com.flag.travelplanner.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean register(User user) {
        if (userRepository.create(user) > 0) return true;
        return false;
    }

    @Override
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean login() {
        return false;
    }
}
