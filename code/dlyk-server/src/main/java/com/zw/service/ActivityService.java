package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.dto.ActivityPageQueryDTO;
import com.zw.entity.TActivity;
import com.zw.vo.ActivityVO;

public interface ActivityService {
    /**
     * 分页查询活动数据
     * @param activityPageQueryDTO
     * @return
     */
    PageInfo<ActivityVO> activityPage(ActivityPageQueryDTO activityPageQueryDTO);

    /**
     * 保存活动数据
     * @param tActivity
     */
    void saveActivity(TActivity tActivity);

    /**
     * 根据id查询活动详情
     * @param id
     * @return
     */
    ActivityVO activityDetail(Integer id);


    /**
     * 修改活动数据
     * @param tActivity
     */
    void updateActivity(TActivity tActivity);
}
