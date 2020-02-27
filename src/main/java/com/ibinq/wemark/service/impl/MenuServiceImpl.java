package com.ibinq.wemark.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ibinq.wemark.bean.Menu;
import com.ibinq.wemark.dao.MenuDao;
import com.ibinq.wemark.service.MenuService;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {
}
