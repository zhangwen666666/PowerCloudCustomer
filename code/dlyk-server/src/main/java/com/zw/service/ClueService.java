package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TClue;
import com.zw.vo.ClueVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClueService {
    /**
     * 分页查询线索列表数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<ClueVO> cluePage(Integer pageNum, Integer pageSize);

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

    /**
     * 检查手机号是否存在
     * @param phone
     * @return
     */
    boolean checkPhone(String phone);

    /**
     * 新增线索
     * @param tClue
     */
    void saveClue(TClue tClue);

    /**
     * 根据id查询线索详情
     * @param id
     * @return
     */
    ClueVO clueDetail(Integer id);

    /**
     * 编辑线索
     * @param tClue
     */
    void updateClue(TClue tClue);
}
