package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.result.R;
import com.zw.service.ActivityService;
import com.zw.vo.ActivityVO;
import com.zw.vo.OwnerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 分页查询活动列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/api/activity/list")
    public R<PageInfo<ActivityVO>> activityPage(Integer pageNum, Integer pageSize){
        PageInfo<ActivityVO> pageInfo = activityService.activityPage(pageNum, pageSize);
        return R.OK(pageInfo);
    }

    @GetMapping("/api/activity/ownerList")
    public R<List<OwnerVO>> ownerList(){
        List<OwnerVO> ownerList = activityService.getOwnerList();
        return R.OK(ownerList);
    }
}
