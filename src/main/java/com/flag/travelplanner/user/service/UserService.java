package com.flag.travelplanner.user.service;

import com.flag.travelplanner.user.entity.User;

public interface UserService {
     boolean register(User user);
     User getUserByUserName(String userName);
     boolean login();
}
