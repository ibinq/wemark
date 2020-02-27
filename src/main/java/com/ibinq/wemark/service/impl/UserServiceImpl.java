package com.ibinq.wemark.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.extra.servlet.ServletUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ibinq.wemark.bean.User;
import com.ibinq.wemark.common.Constent;
import com.ibinq.wemark.common.Result;
import com.ibinq.wemark.dao.UserDao;
import com.ibinq.wemark.redis.RedisServiceImpl;
import com.ibinq.wemark.service.UserService;
import com.ibinq.wemark.util.JwtUtils;
import com.ibinq.wemark.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    UserDao userDao;
    @Autowired
    RedisServiceImpl redisService;

    @Override
    public Result save(User user) {
       // Assert.isNull(user,"参数不能为空");
        // 判断用户名是否存在
        User u = userDao.getUserByUsername(user.getUsername());
        if (u != null ){
            return Result.fail("用户名已存在");
        }
        // 判断邮箱是否存在
        u = userDao.getUserByEmail(user.getEmail());
        if (u != null){
            return Result.fail("邮箱已存在");
        }
        //密码加密
        user.setPassword(Md5Util.MD5EncodeUtf8(user.getPassword()+Constent.LOGIN_SALT));
        return Result.ok(userDao.save(user));
    }

    @Override
    public Result login(HttpServletRequest request, String username,String password) {
        User user = Validator.isEmpty(username) ?   userDao.getUserByEmail(username):userDao.getUserByUsername(username);
        if (user == null)
            return Result.fail("账号不存在！");
        //密码加密
        if (!user.getPassword().equals(Md5Util.MD5EncodeUtf8(password+Constent.LOGIN_SALT))){
            return Result.fail("密码错误！");
        }
        return Result.ok( getToken(user));
    }

    @Override
    public User selectById(Long id) {
        return userDao.selectById(id);
    }

    public String getToken(User user) {
        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("username",user.getUsername());
       return JwtUtils.generateToken(userInfoMap);
    }

}
