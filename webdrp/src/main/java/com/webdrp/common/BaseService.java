package com.webdrp.common;


import java.io.Serializable;
import java.util.List;

/**
 * Created by yuanming on 2018/8/2.
 */
public interface BaseService<T> {
    /**
     * 新增一个
     * @param t
     * @return
     */
    void add(T t) throws Exception;

    /**
     * 根据ID删除
     * @param t
     */
    void delete(T t);

    /**
     *改
     */
    void update(T t);


    /**
     * 查找一个
     * @param
     * @return
     */
    T findOne(Serializable t);

    /**
     * 查找所有
     * @return
     */
    List<T> findAll(T t);

    List<T> loadAll(T t,Pager pager);

    Pager getPager(long countAll,Pager pager);

    long count(T t);
}
