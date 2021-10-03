package com.flag.travelplanner.user.controller;


import com.flag.travelplanner.user.entity.User;
import com.flag.travelplanner.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value="/register")
    @ResponseStatus(value= HttpStatus.CREATED)
    public void register(@RequestBody User user) {
        userService.register(user);
    }

    @RequestMapping("/login")
    public void login(@RequestParam(value = "error") String error, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("message", "bad credentials");
        response.getOutputStream()
                .println(data.toString());
    }

    @GetMapping(value="/{user_name}")
    @ResponseBody
    public User getUserById(@PathVariable("user_name") String userId) {
        return userService.getUserByUserName(userId);
    }
}
