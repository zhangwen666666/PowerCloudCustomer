package com.zw.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.zw.entity.TClue;
import com.zw.mapper.TClueMapper;
import com.zw.util.UserInfoUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

/**
 * 每读一行Excel的数据，就会出发invoke方法
 * 读完之后会触发一下doAfterAllAnalysed方法
 */
@Slf4j
public class UploadDataListener implements ReadListener<TClue> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private List<TClue> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private TClueMapper tClueMapper;

    public UploadDataListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
    }

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param tClueMapper
     */
    public UploadDataListener(TClueMapper tClueMapper) {
        this.tClueMapper = tClueMapper;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(TClue data, AnalysisContext context) {
        // log.info("解析到一条数据:{}", JSONUtils.toJSON(data));
        data.setCreateBy(UserInfoUtil.getCurrentUser().getId());
        data.setCreateTime(new Date());
        data.setEditTime(new Date());
        data.setEditBy(UserInfoUtil.getCurrentUser().getId());
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if(!cachedDataList.isEmpty()){
            saveData();
        }
        // log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        // log.info("{}条数据，开始存储数据库！", cachedDataList.size());
         tClueMapper.saveClueList(cachedDataList);
        // log.info("存储数据库成功！");
    }
}
