package com.zw.service;

import com.github.pagehelper.PageInfo;
import com.zw.entity.TClueRemark;
import com.zw.vo.ClueRemarkVO;

public interface ClueRemarkService {
    /**
     * 新增线索备注记录
     * @param tClueRemark
     */
    void addClueRemark(TClueRemark tClueRemark);

    /**
     * 线索备注记录分页查询
     * @param pageNum
     * @param pageSize
     * @param clueId
     * @return
     */
    PageInfo<ClueRemarkVO> clueRemarkPage(Integer pageNum, Integer pageSize, Integer clueId);

    /**
     * 修改线索备注记录
     * @param tClueRemark
     */
    void updateClueRemark(TClueRemark tClueRemark);

    /**
     * 删除线索备注记录
     * @param id
     */
    void deleteClueRemark(Integer id);
}
