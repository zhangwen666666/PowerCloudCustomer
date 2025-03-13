package com.zw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TActivityRemark;
import com.zw.mapper.TActivityRemarkMapper;
import com.zw.service.ActivityRemarkService;
import com.zw.vo.ActivityRemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private TActivityRemarkMapper tActivityRemarkMapper;

    /**
     * 新增活动备注
     * @param tActivityRemark
     */
    public void addActivityRemark(TActivityRemark tActivityRemark) {
        tActivityRemarkMapper.insert(tActivityRemark);
    }

    /**
     * 根据activityId分页查询活动的备注
     * @param pageNum
     * @param pageSize
     * @param activityId
     * @return
     */
    public PageInfo<ActivityRemarkVO> activityRemarkPage(Integer pageNum, Integer pageSize, Integer activityId) {
        PageHelper.startPage(pageNum, pageSize);
        List<ActivityRemarkVO> activityRemarkVOList = tActivityRemarkMapper.selectAll(new FilterSqlDTO(), activityId);
        return new PageInfo<>(activityRemarkVOList);
    }
}
