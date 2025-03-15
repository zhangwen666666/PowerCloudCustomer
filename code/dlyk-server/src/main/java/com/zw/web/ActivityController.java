package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.dto.ActivityPageQueryDTO;
import com.zw.entity.TActivity;
import com.zw.result.R;
import com.zw.service.ActivityService;
import com.zw.vo.ActivityVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 分页查询活动列表
     * @param activityPageQueryDTO
     * @return
     */
    @GetMapping("/api/activity/list")
    public R<PageInfo<ActivityVO>> activityPage(ActivityPageQueryDTO activityPageQueryDTO){
        // System.out.println(activityPageQueryDTO);
        PageInfo<ActivityVO> pageInfo = activityService.activityPage(activityPageQueryDTO);
        return R.OK(pageInfo);
    }

    /**
     * 新增活动
     * @param tActivity
     * @return
     */
    @PostMapping("/api/activity")
    public R saveActivity(@RequestBody TActivity tActivity){
        activityService.saveActivity(tActivity);
        return R.OK();
    }

    /**
     * 根据id查询活动详情
     * @param id
     * @return
     */
    @GetMapping("/api/activity/{id}")
    public R<ActivityVO> activityDetail(@PathVariable("id") Integer id){
        ActivityVO activityVO = activityService.activityDetail(id);
        return R.OK(activityVO);
    }

    /**
     * 修改活动
     * @param tActivity
     * @return
     */
    @PutMapping("/api/activity")
    public R updateActivity(@RequestBody TActivity tActivity){
        activityService.updateActivity(tActivity);
        return R.OK();
    }


    /**
     * 删除活动
     * @param id
     * @return
     */
    @DeleteMapping("/api/activity/{id}")
    public R deleteActivity(@PathVariable("id") Integer id){
        activityService.deleteActivity(id);
        return R.OK();
    }

    @DeleteMapping("/api/activity/delBatch")
    public R deleteBatch(@RequestParam List<Integer> ids){
        // System.out.println(ids);
        activityService.deleteBatch(ids);
        return R.OK();
    }
}
