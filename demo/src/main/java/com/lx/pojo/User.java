package com.lx.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements Serializable {

    @TableId
    private Long id;
    @JsonProperty("uName")
    private String uName;

    private String pwd;

    private String status; // 0 正常 1 停用

    private String email;

    private String sex; // 0 女 1 男 2 未知

    private String userType; // 0 管理员 1 普通用户

    private String avatar; // 头像

    private Long createBy; // 创建人id

    private Date createTime; // 创建时间

    private Long updateBy; // 更新人

    private Date updateTime; // 更新时间
    @TableLogic
    private int delFlag; // 0 未删除 1 已删除
}
