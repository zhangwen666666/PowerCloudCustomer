package com.zw.web;

import com.github.pagehelper.PageInfo;
import com.zw.dto.CluePageQueryDTO;
import com.zw.result.R;
import com.zw.service.ClueService;
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
    public R<PageInfo<CluePageQueryDTO>> cluePage(Integer pageNum, Integer pageSize){
        PageInfo<CluePageQueryDTO> pageInfo = clueService.cluePage(pageNum, pageSize);
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
    public R deleteClueById(Integer id){
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
}
