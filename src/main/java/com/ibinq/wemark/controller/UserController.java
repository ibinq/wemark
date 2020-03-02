package com.ibinq.wemark.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ibinq.wemark.annotations.PassToken;
import com.ibinq.wemark.annotations.UserLoginToken;
import com.ibinq.wemark.bean.Menu;
import com.ibinq.wemark.bean.User;
import com.ibinq.wemark.common.Result;
import com.ibinq.wemark.redis.RedisServiceImpl;
import com.ibinq.wemark.service.UserService;
import com.ibinq.wemark.vo.MenuVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/user")
@Slf4j
@Api(value = "用户")
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
    public  Result login(HttpServletRequest request,@RequestParam() String username,@RequestParam()String password){
        return userService.login(request,username,password);
    }

    @PostMapping("/test")
    public  Result test(){
        return Result.ok();
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user){
        if (userService.updateById(user)){
            return Result.ok();
        }
        return Result.fail();
    }
    @PostMapping("/delete")
    public Result delete( Long id){
        if (userService.deleteById(id)){
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping("/list")
    public Result list(String query , Integer pageNum,Integer pageSize){
        EntityWrapper entityWrapper = new EntityWrapper();
        if (StringUtils.isNotBlank(query)){
            entityWrapper.like("username",query);
        }
        Page<User> page = new Page<>(pageNum, pageSize);//分页类
        List<User> list = userService.selectPage(page, entityWrapper).getRecords();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("users",list);
        map.put("total",page.getTotal());
        return  Result.ok(map);
    }
}
