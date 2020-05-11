package com.webdrp.common;

import com.webdrp.entity.Record;
import com.webdrp.entity.dto.RecordDto;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yuanming on 2018/8/2.
 */
public interface BaseMapper<T> {

    void insert(T var);

    void delete(T var);

    void update(T var);

    List<T> findAll(@Param(value = "entity")T var);

    T findById(@Param("id") Serializable id);

    List<T> loadAll(@Param(value = "entity") T entity, @Param(value = "pager") Pager pager);

    long count(@Param(value = "entity")T entity);


}
