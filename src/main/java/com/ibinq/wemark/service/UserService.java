package com.ibinq.wemark.service;

import com.baomidou.mybatisplus.service.IService;
import com.ibinq.wemark.bean.User;
import com.ibinq.wemark.common.Result;

import javax.servlet.http.HttpServletRequest;


public interface UserService extends IService<User> {
    Result save(User user);

    Result login(HttpServletRequest request, String username,String password);

    User selectById(Long id);
}
