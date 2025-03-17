package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TClue;
import com.zw.result.R;
import com.zw.service.ClueService;
import com.zw.vo.ClueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ClueController {
    @Autowired
    private ClueService clueService;

    /**
     * 分页查询线索列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/api/clue/list")
    public R<PageInfo<ClueVO>> cluePage(Integer pageNum, Integer pageSize){
        PageInfo<ClueVO> pageInfo = clueService.cluePage(pageNum, pageSize);
        return R.OK(pageInfo);
    }

    /**
     * 导入Excel文件
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/api/importExcel")
    public R importExcel(MultipartFile file) throws Exception {
        // System.out.println(file);
        clueService.importExcel(file);
        return R.OK();
    }

    /**
     * 根据id删除线索
     * @param id
     * @return
     */
    @DeleteMapping("/api/clue/{id}")
    public R deleteClueById(@PathVariable Integer id){
        clueService.deleteClueById(id);
        return R.OK();
    }

    /**
     * 根据id集合删除线索
     * @param ids
     * @return
     */
    @DeleteMapping("/api/clue/batchDelete")
    public R batchDelete(@RequestParam List<Integer> ids){
        clueService.batchDelete(ids);
        return R.OK();
    }

    /**
     * 检查手机号是否存在了
     * @param phone
     * @return
     */
    @GetMapping("/api/clue/checkPhone/{phone}")
    public R<Boolean> checkPhone(@PathVariable String phone){
        boolean flag = clueService.checkPhone(phone);
        return R.OK(flag);
    }

    /**
     * 新增线索
     * @param tClue
     */
    @PostMapping("api/clue")
    public R saveClue(@RequestBody TClue tClue){
        clueService.saveClue(tClue);
        return R.OK();
    }

    /**
     * 根据id查询线索详情
     * @param id
     * @return
     */
    @GetMapping("/api/clue/{id}")
    public R<ClueVO> clueDetail(@PathVariable("id") Integer id){
        ClueVO clueVO = clueService.clueDetail(id);
        return R.OK(clueVO);
    }

    /**
     * 编辑线索
     * @param tClue
     * @return
     */
    @PutMapping("api/clue")
    public R updateClue(@RequestBody TClue tClue){
        clueService.updateClue(tClue);
        return R.OK();
    }
}
