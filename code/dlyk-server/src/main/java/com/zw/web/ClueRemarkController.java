package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TClueRemark;
import com.zw.result.R;
import com.zw.service.ClueRemarkService;
import com.zw.vo.ClueRemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClueRemarkController {
    @Autowired
    private ClueRemarkService clueRemarkService;

    /**
     * 新增线索备注记录
     * @param tClueRemark
     * @return
     */
    @PostMapping("/api/clue/remark")
    public R addClueRemark(@RequestBody TClueRemark tClueRemark){
        clueRemarkService.addClueRemark(tClueRemark);
        return R.OK();
    }

    /**
     * 线索备注记录分页查询
     * @param pageNum
     * @param pageSize
     * @param clueId
     * @return
     */
    @GetMapping("/api/clue/remark")
    public R<PageInfo<ClueRemarkVO>> clueRemarkPage(Integer pageNum, Integer pageSize, Integer clueId){
        PageInfo<ClueRemarkVO> pageInfo = clueRemarkService.clueRemarkPage(pageNum, pageSize, clueId);
        return R.OK(pageInfo);
    }


    /**
     * 修改线索备注记录
     * @param tClueRemark
     * @return
     */
    @PutMapping("/api/clue/remark")
    public R updateClueRemark(@RequestBody TClueRemark tClueRemark){
        clueRemarkService.updateClueRemark(tClueRemark);
        return R.OK();
    }


    /**
     * 删除线索备注记录
     * @param id
     * @return
     */
    @DeleteMapping("/api/clue/remark/{id}")
    public R deleteClueRemark(@PathVariable("id") Integer id){
        clueRemarkService.deleteClueRemark(id);
        return R.OK();
    }
}
