package com.zw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TClueRemark;
import com.zw.mapper.TClueRemarkMapper;
import com.zw.service.ClueRemarkService;
import com.zw.vo.ClueRemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Autowired
    private TClueRemarkMapper tClueRemarkMapper;

    /**
     * 新增线索备注记录
     * @param tClueRemark
     */
    @Override
    public void addClueRemark(TClueRemark tClueRemark) {
        tClueRemark.setDeleted(0);
        tClueRemarkMapper.insert(tClueRemark);
    }


    /**
     * 线索备注记录分页查询
     * @param pageNum
     * @param pageSize
     * @param clueId
     * @return
     */
    @Override
    public PageInfo<ClueRemarkVO> clueRemarkPage(Integer pageNum, Integer pageSize, Integer clueId) {
        PageHelper.startPage(pageNum, pageSize);
        List<ClueRemarkVO> clueRemarkVOList = tClueRemarkMapper.selectAll(new FilterSqlDTO(), clueId);
        return new PageInfo<>(clueRemarkVOList);
    }


    /**
     * 修改线索备注记录
     * @param tClueRemark
     */
    @Override
    public void updateClueRemark(TClueRemark tClueRemark) {
        tClueRemarkMapper.updateByPrimaryKeySelective(tClueRemark);
    }


    /**
     * 删除线索备注记录(逻辑删除)
     * @param id
     */
    @Override
    public void deleteClueRemark(Integer id) {
        TClueRemark remark = new TClueRemark();
        remark.setId(id);
        remark.setDeleted(1);
        tClueRemarkMapper.updateByPrimaryKeySelective(remark);
    }
}
