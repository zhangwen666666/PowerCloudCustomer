package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TActivityRemark;
import com.zw.result.R;
import com.zw.service.ActivityRemarkService;
import com.zw.vo.ActivityRemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ActivityRemarkController {
    @Autowired
    private ActivityRemarkService activityRemarkService;

    /**
     * 新增活动备注
     * @param tActivityRemark
     * @return
     */
    @PostMapping("/api/activity/remark")
    public R addActivityRemark(@RequestBody TActivityRemark tActivityRemark){
        activityRemarkService.addActivityRemark(tActivityRemark);
        return R.OK();
    }

    /**
     * 根据activityId分页查询活动的备注
     * @param pageNum
     * @param pageSize
     * @param activityId
     * @return
     */
    @GetMapping("/api/activity/remark")
    public R<PageInfo<ActivityRemarkVO>> activityRemarkPage(Integer pageNum, Integer pageSize, Integer activityId){
        PageInfo<ActivityRemarkVO> pageInfo = activityRemarkService.activityRemarkPage(pageNum, pageSize, activityId);
        return R.OK(pageInfo);
    }

    /**
     * 修改活动备注
     * @param tActivityRemark
     * @return
     */
    @PutMapping("/api/activity/remark")
    public R editActivityRemark(@RequestBody TActivityRemark tActivityRemark){
        activityRemarkService.editActivityRemark(tActivityRemark);
        return R.OK();
    }


    /**
     * 删除活动备注
     * @param id
     * @return
     */
    @DeleteMapping("/api/activity/remark/{id}")
    public R deleteActivityRemark(@PathVariable Integer id){
        activityRemarkService.deleteActivityRemark(id);
        return R.OK();
    }
}
