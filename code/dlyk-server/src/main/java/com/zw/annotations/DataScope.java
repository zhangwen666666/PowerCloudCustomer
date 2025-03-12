package com.zw.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataScope {
    /**
     * 要在sql语句的末尾添加一个过滤条件
     *
     * 例如查询用户信息
     * select * from t_user (管理员)
     * select * from t_user where id = xxx (xxx用户)
     *
     * 查询用户的活动
     * select * from t_activity (管理员)
     * select * from t_activity where owner_id = xxx (xxx用户)
     */

    /**
     * 表的别名
     * @return
     */
    public String tableAlias() default "";

    /**
     * 字段的别名
     * @return
     */
    public String tableFiled() default "";
}
