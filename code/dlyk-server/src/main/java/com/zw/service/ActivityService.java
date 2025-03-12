package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.vo.ActivityVO;
import com.zw.vo.OwnerVO;

import java.util.List;

public interface ActivityService {
    /**
     * 分页查询活动数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ActivityVO> activityPage(Integer pageNum, Integer pageSize);

    /**
     * 获取负责人列表
     * @return
     */
    List<OwnerVO> getOwnerList();
}
