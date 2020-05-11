package com.webdrp.log.annnotion;

import java.lang.annotation.*;

/**
 * @Author: zhang yuan ming
 * @Date: create in 下午2:19 2018/2/18
 * @mail: zh878998515@gmail.com
 * @Description:
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
    /**
     * 说明
     * @return
     */
    String description() default "";

}
