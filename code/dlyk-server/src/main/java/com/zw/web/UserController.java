package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TUser;
import com.zw.result.R;
import com.zw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /**
     *
     * 获取当前登录的用户信息
     * @param authentication
     * @return
     */
    @GetMapping("/api/login/info")
    public R<TUser> loginInfo(Authentication authentication){
        TUser tUser = (TUser) authentication.getPrincipal();
        return R.OK(tUser);
    }

    /**
     * 免登录接口
     * @return
     */
    @GetMapping("/api/login/free")
    public R freeLogin(){
        // 既然能进入Controller，说明在拦截器中token验证通过
        return R.OK();
    }

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/api/user/list")
    public R<PageInfo<TUser>> userPage(Integer pageNum, Integer pageSize){
        PageInfo<TUser> pageInfo = userService.userPage(pageNum, pageSize);
        return R.OK(pageInfo);
    }

    /**
     * 根据id查询用户详情
     * @param id
     * @return
     */
    @GetMapping("/api/user/{id}")
    public R<TUser> userDetail(@PathVariable("id") Integer id){
        TUser user = userService.userDetail(id);
        return R.OK(user);
    }
}
