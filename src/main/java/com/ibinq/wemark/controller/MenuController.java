package com.ibinq.wemark.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ibinq.wemark.bean.Menu;
import com.ibinq.wemark.common.Result;
import com.ibinq.wemark.service.MenuService;
import com.ibinq.wemark.vo.MenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.query.ExampleQueryMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Autowired
    MenuService menuService;

    @PostMapping("/add")
    public Result add(Menu menu){
        if(menuService.insert(menu)){
            return Result.ok();
        }
        return Result.fail();
    }

    @GetMapping("/list")
    public Result list(){
        EntityWrapper entityWrapper = new EntityWrapper();
        List<Menu> list = menuService.selectList(entityWrapper);
        ArrayList<MenuVo> menuVos = new ArrayList<>();
        ArrayList<MenuVo> children = new ArrayList<>();
        for (Menu m : list) {
            MenuVo menuVo = new MenuVo();
            menuVo.setIcon(m.getIcon());
            menuVo.setId(m.getId());
            menuVo.setName(m.getName());
            menuVo.setUrl(m.getUrl());
            menuVo.setParentId(m.getParentId());
            if (m.getParentId() == 0){
                menuVos.add(menuVo);
            }else {
                children.add(menuVo);
            }
        }
        for (MenuVo menuVo : menuVos){
            for (MenuVo child : children){
                if (menuVo.getId() == child.getParentId()){
                    List<MenuVo> vos = menuVo.getChildren();
                    if (vos == null)
                        vos =  new ArrayList<MenuVo>();
                    vos.add(child);
                    menuVo.setChildren(vos);
                }
            }
        }
        return  Result.ok(menuVos);
    }
}
