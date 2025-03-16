package com.zw.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.zw.task.DataTask;

/**
 * 将产品名称转换id
 */
public class ProductConverter implements Converter<Object> {
    /**
     * 把Excel中的数据转换为Java类中的数据
     * @param cellData Excel中读取到的数据，如“比亚迪”
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String value = cellData.getStringValue();
        // Excel中读取到的数据，如“比亚迪”,需要查询数据库，获取先生所对应的id
        // 但是这个转化器无法做为SpringBoot容器的Bean，也就不能注入Mapper
        // 因此我们需要一个定时任务来从数据库中读取表，并存储在内存中
        // 然后直接在内存中查询
        return DataTask.productCacheMap.get(value) == null ? -1 : DataTask.productCacheMap.get(value);
    }
}
