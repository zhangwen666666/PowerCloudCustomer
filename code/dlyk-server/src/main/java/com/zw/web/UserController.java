package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.dto.UserSaveDTO;
import com.zw.entity.TUser;
import com.zw.result.R;
import com.zw.service.UserService;
import com.zw.vo.OwnerVO;
import com.zw.vo.UserDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public R<UserDetailVO> userDetail(@PathVariable("id") Integer id){
        UserDetailVO userDetailVO = userService.userDetail(id);
        return R.OK(userDetailVO);
    }

    /**
     * 新增用户
     * @param userSaveDTO
     * @return
     */
    @PostMapping("/api/user")
    public R saveUser(@RequestBody UserSaveDTO userSaveDTO){
        // System.out.println(userSaveDTO);
        int ret = userService.saveUser(userSaveDTO);
        return ret >= 1 ? R.OK() : R.error();
    }

    /**
     * 更新用户
     * @param userSaveDTO
     * @return
     */
    @PutMapping("/api/user")
    public R updateUser(@RequestBody UserSaveDTO userSaveDTO){
         // System.out.println(userSaveDTO);
        int ret = userService.updateUser(userSaveDTO);
        return ret >= 1 ? R.OK() : R.error();
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/api/user/{id}")
    public R deleteUser(@PathVariable("id") Integer id){
        int ret = userService.deleteUser(id);
        return ret >= 1 ? R.OK() : R.error();
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/api/user")
    @Transactional
    public R batchDeleteUser(@RequestParam List<Integer> ids){
        // System.out.println(userSaveDTO);
        int ret = userService.batchDeleteUser(ids);
        return ret >= 1 ? R.OK() : R.error();
    }

    @GetMapping("/api/user/ownerList")
    public R<List<OwnerVO>> ownerList(){
        List<OwnerVO> ownerList = userService.getOwnerList();
        return R.OK(ownerList);
    }
}
