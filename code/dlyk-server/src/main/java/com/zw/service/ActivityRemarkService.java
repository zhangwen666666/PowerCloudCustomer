package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TActivityRemark;
import com.zw.vo.ActivityRemarkVO;

public interface ActivityRemarkService {
    /**
     * 新增活动备注
     * @param tActivityRemark
     */
    void addActivityRemark(TActivityRemark tActivityRemark);

    /**
     * 根据activityId分页查询活动的备注
     * @param pageNum
     * @param pageSize
     * @param activityId
     * @return
     */
    PageInfo<ActivityRemarkVO> activityRemarkPage(Integer pageNum, Integer pageSize, Integer activityId);
}
