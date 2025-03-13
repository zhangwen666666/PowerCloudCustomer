package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TActivityRemark;
import com.zw.result.R;
import com.zw.service.ActivityRemarkService;
import com.zw.vo.ActivityRemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
