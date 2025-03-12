package com.zw.aspect;

import com.zw.annotations.DataScope;
import com.zw.dto.FilterSqlDTO;
import com.zw.entity.TRole;
import com.zw.util.UserInfoUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class DataScopeAspect {
    @Pointcut("@annotation(com.zw.annotations.DataScope) && execution(* com.zw.mapper.*.*(..))")
    private void pointcut() {}

    @Before("pointcut()")
    public void dataScope(JoinPoint joinPoint) {
        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取注解
        DataScope dataScope = signature.getMethod().getAnnotation(DataScope.class);
        // 获取注解中的两个属性值
        String tableAlias = dataScope.tableAlias();
        String tableFiled = dataScope.tableFiled();
        // 获取方法参数
        FilterSqlDTO filterSqlDTO = (FilterSqlDTO) joinPoint.getArgs()[0];
        // 获取用户权限列表
        List<TRole> roleList = UserInfoUtil.getCurrentUser().getRoleList();
        // 要追加的过滤条件
        String filterSql = "";
        if(tableAlias.isEmpty()){
            // 空串，没给表起别名
            filterSql = " and " + tableFiled + "=" + UserInfoUtil.getCurrentUser().getId();
        }else {
            filterSql = " and " + tableAlias + "." + tableFiled + "=" + UserInfoUtil.getCurrentUser().getId();
        }
        if (roleList == null || roleList.isEmpty()){
            // 用户权限列表为空 只查自己的
            filterSqlDTO.filterSql = filterSql;
            return;
        }
        for (int i = 0; i < roleList.size(); i++) {
            if (roleList.get(i).getRole().equals("admin")){
                // 是管理员
                return;
            }
        }
        // 不是管理员
        filterSqlDTO.filterSql = filterSql;
    }
}
