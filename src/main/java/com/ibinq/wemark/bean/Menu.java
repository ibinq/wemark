package com.ibinq.wemark.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("w_menu")
public class Menu {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String parentIds;
    private String name;
    private String url;
    private Integer type;
    private String icon;
    private int orderNum;
    @TableLogic
    private Integer deleted;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
