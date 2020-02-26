package com.ibinq.wemark.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ibinq.wemark.bean.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

    boolean save(User user);

    User getUserByUsername(String username);

    User getUserByEmail(String email);
}
