package com.leyou.user.service.controller;

import com.leyou.user.api.domain.User;
import com.leyou.user.service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @date 2020/3/26
 */
@RestController
public class UserController {
    @Autowired
    IUserService userService;
    @GetMapping("/{data}/{type}")
    public ResponseEntity checkUsernameOrPhone(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        boolean b = userService.checkUsernameOrPhone(data, type);
        if (b) {
            return ResponseEntity.ok(b);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/code")
    public ResponseEntity sendMobileMsg(String phone) {
        boolean b = userService.sendMobileMsg(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/register")
    public ResponseEntity register(User user, @RequestParam("code") String code) {
        boolean register = userService.register(user, code);
        if (!register) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.queryUser(username, password);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }
}
