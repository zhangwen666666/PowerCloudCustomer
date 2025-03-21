package com.zw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.consts.Constant;
import com.zw.dto.FilterSqlDTO;
import com.zw.dto.UserSaveDTO;
import com.zw.entity.TPermission;
import com.zw.entity.TRole;
import com.zw.entity.TUser;
import com.zw.manager.RedisManager;
import com.zw.mapper.TPermissionMapper;
import com.zw.mapper.TRoleMapper;
import com.zw.mapper.TUserMapper;
import com.zw.service.UserService;
import com.zw.util.CacheUtil;
import com.zw.vo.OwnerVO;
import com.zw.vo.UserDetailVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TUserMapper tUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TRoleMapper tRoleMapper;
    @Autowired
    private RedisManager redisManager;
    @Autowired
    private TPermissionMapper tPermissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser user = tUserMapper.selectByLoginAct(username);
        if (user == null) {
            throw new UsernameNotFoundException("登录账号不存在");
        }

        List<TRole> tRoleList = tRoleMapper.selectByUserId(user.getId());
        user.setRoleList(tRoleList);

        List<TPermission> menuPermissionList = tPermissionMapper.selectMenuPermissionByUserId(user.getId());
        user.setMenuPermissionList(menuPermissionList);

        List<TPermission> buttonPermissionList = tPermissionMapper.selectButtonPermissionByUserId(user.getId());
        user.setPermissionList(buttonPermissionList);

        return user;
    }


    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<TUser> userPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TUser> userList = tUserMapper.selectAll(new FilterSqlDTO());
        return new PageInfo<>(userList);
    }


    /**
     * 根据id查询用户详情
     * @param id
     * @return
     */
    @Override
    public UserDetailVO userDetail(Integer id) {
        return tUserMapper.selectDetailById(id);
    }

    /**
     * 新增用户
     * @param userSaveDTO
     * @return
     */
    @Override
    public int saveUser(UserSaveDTO userSaveDTO) {
        TUser user = new TUser();
        BeanUtils.copyProperties(userSaveDTO, user);
        // 密码加密
        String password = passwordEncoder.encode(userSaveDTO.getLoginPwd());
        user.setLoginPwd(password);
        return tUserMapper.insertSelective(user);
    }


    /**
     * 编辑用户
     * @param userSaveDTO
     * @return
     */
    @Override
    public int updateUser(UserSaveDTO userSaveDTO) {
        TUser user = new TUser();
        BeanUtils.copyProperties(userSaveDTO, user);
        if (StringUtils.hasText(userSaveDTO.getLoginPwd())){
            // 如果传过来了新密码，密码加密
            String password = passwordEncoder.encode(userSaveDTO.getLoginPwd());
            user.setLoginPwd(password);
        }
        return tUserMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 根据id删除单个用户
     * @param id
     * @return
     */
    @Override
    public int deleteUser(Integer id) {
        return tUserMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id批量删除用户
     * @param ids
     * @return
     */
    @Override
    public int batchDeleteUser(List<Integer> ids) {
        return tUserMapper.deleteByIds(ids);
    }


    /**
     * 获取负责人列表
     * @return
     */
    @Override
    public List<OwnerVO> getOwnerList() {
//        // 如果不是管理员，则只查询自己的活动，因此负责人列表也只有自己
//        if (!UserInfoUtil.isAdmin()) {
//            OwnerVO ownerVO = new OwnerVO(UserInfoUtil.getCurrentUser().getId(), UserInfoUtil.getCurrentUser().getName());
//            return List.of(ownerVO);
//        }
        // 使用带有缓存的查询工具方法，获取所有的负责人姓名和id
        return CacheUtil.getCacheData(() -> {
            // 从缓存查数据
            return (List<OwnerVO>) redisManager.getValue(Constant.REDIS_OWNER_KEY);
        }, () -> {
            // 从数据库查数据
            return tUserMapper.getOwnerList();
        }, (t) -> {
            // 存入redis中
            redisManager.setValue(Constant.REDIS_OWNER_KEY, t);
        });
    }
}
