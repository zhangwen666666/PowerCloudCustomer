package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.dto.CluePageQueryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClueService {
    /**
     * 分页查询线索列表数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<CluePageQueryDTO> cluePage(Integer pageNum, Integer pageSize);

    /**
     * 导入excel文件
     * @param file
     */
    void importExcel(MultipartFile file) throws Exception;

    /**
     * 根据id删除线索
     * @param id
     */
    void deleteClueById(Integer id);

    /**
     * 根据id集合批量删除线索
     * @param ids
     */
    void batchDelete(List<Integer> ids);
}
