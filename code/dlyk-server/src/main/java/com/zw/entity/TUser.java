package com.zw.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 * t_user
 */
@Data
public class TUser implements Serializable , UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键，自动增长，用户ID
     */
    private Integer id;

    /**
     * 登录账号
     */
    private String loginAct;

    /**
     * 登录密码
     */
    @JsonIgnore
    private String loginPwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 账户是否没有过期，0已过期 1正常
     */
    private Integer accountNoExpired;

    /**
     * 密码是否没有过期，0已过期 1正常
     */
    private Integer credentialsNoExpired;

    /**
     * 账号是否没有锁定，0已锁定 1正常
     */
    private Integer accountNoLocked;

    /**
     * 账号是否启用，0禁用 1启用
     */
    private Integer accountEnabled;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人
     */
    private Integer createBy;

    /**
     * 编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editTime;

    /**
     * 编辑人
     */
    private Integer editBy;

    /**
     * 最近登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastLoginTime;

    /**
     * 用户的角色列表
     */
    private List<String> roleList;

    /**
     * 用户的权限列表（存放权限标识符）
     */
    private List<String> permissionList;

    // --------------------实现UserDetails接口的7个方法----------------------

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        // 角色
        if (!ObjectUtils.isEmpty(roleList)){
            roleList.forEach(role -> {
                list.add(new SimpleGrantedAuthority("ROLE_" + role));
            });
        }
        // 权限标识符
        if (!ObjectUtils.isEmpty(permissionList)) {
            permissionList.forEach(permission -> {
                list.add(new SimpleGrantedAuthority(permission));
            });
        }
        return list;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.getLoginPwd();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getLoginAct();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.accountNoExpired == 1;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.accountNoLocked == 1;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return this.credentialsNoExpired == 1;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.accountEnabled == 1;
    }
}