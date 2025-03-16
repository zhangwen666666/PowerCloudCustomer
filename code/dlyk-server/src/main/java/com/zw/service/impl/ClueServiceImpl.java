package com.zw.service.impl;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zw.dto.CluePageQueryDTO;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TClue;
import com.zw.listener.UploadDataListener;
import com.zw.mapper.TClueMapper;
import com.zw.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private TClueMapper tClueMapper;

    /**
     * 分页查询线索列表数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<CluePageQueryDTO> cluePage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<CluePageQueryDTO> cluePagelist = tClueMapper.selectAll(new FilterSqlDTO());
        return new PageInfo<>(cluePagelist);
    }

    /**
     * 导入Excel
     * @param file
     */
    @Override
    public void importExcel(MultipartFile file) throws Exception{
        InputStream inputStream = file.getInputStream();
        EasyExcel.read(inputStream, TClue.class,
                new UploadDataListener(tClueMapper)).sheet().doRead();
    }

    /**
     * 根据id删除线索
     * @param id
     */
    @Override
    public void deleteClueById(Integer id) {
        tClueMapper.deleteByPrimaryKey(id);
    }


    /**
     * 根据id集合批量删除线索
     * @param ids
     */
    @Override
    public void batchDelete(List<Integer> ids) {
        tClueMapper.batchDelete(ids);
    }
}
