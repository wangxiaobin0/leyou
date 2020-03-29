package com.leyou.auth.service.api;

import com.leyou.user.api.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 * @date 2020/3/28
 */
@FeignClient("leyou-user")
@RequestMapping("/user")
public interface IUserApi {
    @GetMapping("/query")
    User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}