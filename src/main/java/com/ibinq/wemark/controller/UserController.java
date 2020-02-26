package com.ibinq.wemark.controller;

import com.ibinq.wemark.annotations.PassToken;
import com.ibinq.wemark.annotations.UserLoginToken;
import com.ibinq.wemark.bean.User;
import com.ibinq.wemark.common.Result;
import com.ibinq.wemark.redis.RedisServiceImpl;
import com.ibinq.wemark.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RedisServiceImpl redisService;

    @GetMapping("{id:\\d+}")
    public Result info(@PathVariable("id") Long id){
        return Result.ok(userService.selectById(id)) ;
    }

    @PostMapping("/register")
    public  Result register( User user){
        return userService.save(user);
    }

    @PostMapping("/login")
    public  Result login(HttpServletRequest request, String username,String password){
        return userService.login(request,username,password);
    }

    @PostMapping("/test")
    public  Result test(){
        return Result.ok();
    }

}
