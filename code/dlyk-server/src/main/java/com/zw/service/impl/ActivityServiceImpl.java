package com.zw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.dto.FilterSqlDTO;
import com.zw.mapper.TActivityMapper;
import com.zw.service.ActivityService;
import com.zw.util.UserInfoUtil;
import com.zw.vo.ActivityVO;
import com.zw.vo.OwnerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private TActivityMapper tActivityMapper;

    /**
     * 分页查询活动数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<ActivityVO> activityPage(Integer pageNum, Integer pageSize) {
        if (pageNum == null){
            pageNum = 1;
        }
        if (pageSize == null){
            pageSize = 10;
        }
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityVO> userList = tActivityMapper.selectAll(new FilterSqlDTO());
        return new PageInfo<>(userList);
    }


    /**
     * 获取负责人列表
     * @return
     */
    @Override
    public List<OwnerVO> getOwnerList() {
        // 如果不是管理员，则只查询自己的活动，因此负责人列表也只有自己
        if (!UserInfoUtil.isAdmin()) {
            OwnerVO ownerVO = new OwnerVO(UserInfoUtil.getCurrentUser().getId(), UserInfoUtil.getCurrentUser().getName());
            return List.of(ownerVO);
        }
        // 查询数据库，获取所有的负责人姓名和id
        return tActivityMapper.getOwnerList(new FilterSqlDTO());
    }
}
