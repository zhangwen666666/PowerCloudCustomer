package com.zw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.dto.ActivityPageQueryDTO;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TActivity;
import com.zw.mapper.TActivityMapper;
import com.zw.service.ActivityService;
import com.zw.vo.ActivityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private TActivityMapper tActivityMapper;

    /**
     * 分页查询活动数据
     * @return
     */
    @Override
    public PageInfo<ActivityVO> activityPage(ActivityPageQueryDTO activityPageQueryDTO) {
        PageHelper.startPage(activityPageQueryDTO.getPageNum(), activityPageQueryDTO.getPageSize());
        List<ActivityVO> userList = tActivityMapper.selectAll(new FilterSqlDTO(), activityPageQueryDTO);
        return new PageInfo<>(userList);
    }

    /**
     * 保存活动数据
     * @param tActivity
     */
    @Override
    public void saveActivity(TActivity tActivity) {
        tActivityMapper.insert(tActivity);
    }

    /**
     * 根据id查询活动详情
     * @param id
     * @return
     */
    @Override
    public ActivityVO activityDetail(Integer id) {
        return tActivityMapper.selectDetailById(id);
    }

    /**
     * 修改活动数据
     * @param tActivity
     */
    @Override
    public void updateActivity(TActivity tActivity) {
       tActivityMapper.updateByPrimaryKeySelective(tActivity);
    }


    /**
     * 删除活动数据
     * @param id
     */
    @Override
    public void deleteActivity(Integer id) {
        tActivityMapper.deleteByPrimaryKey(id);
    }


    /**
     * 根据id集合批量删除活动数据
     * @param ids
     */
    @Override
    public void deleteBatch(List<Integer> ids) {
       tActivityMapper.deleteBatchByIds(ids);
    }

    /**
     * 查询所有活动数据
     * @return
     *
    */
    @Override
    public List<TActivity> getAllActivity() {
        return tActivityMapper.getAll();
    }
}
