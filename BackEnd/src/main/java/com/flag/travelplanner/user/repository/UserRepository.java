package com.flag.travelplanner.user.repository;

import com.flag.travelplanner.user.entity.User;

public interface UserRepository {
    long count();
    int create(User user);
    User findByUsername(String username);
    User findByEmail(String email);
    int update(User user);
    int deleteByUsername(String username);
}
