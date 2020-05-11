package com.webdrp.common;


import com.webdrp.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by yuanming on 2018/8/2.
 */
@Transactional
public abstract class BaseServiceImpl<T extends BaseBean,E extends BaseMapper<T>> implements BaseService<T> {

    @Autowired
    private E e;

    public E getE() {
        return e;
    }

    @Override
    public void add(T t) {
        t.beforeCreate();
        e.insert(t);
    }

    @Override
    public void delete(T t) {
        t.beforeDelete();
        e.update(t);
    }

    @Override
    public void update(T t) {
        t.setUpdateTime(DateUtils.dateToString(new Date()));
        e.update(t);
    }

    @Override
    public List<T> findAll(T t) {
        return e.findAll(t);
    }


    @Override
    public T findOne(Serializable t) {
        return e.findById(t);
    }


    public List<T> loadAll(T t,Pager pager){
        long countAll = e.count(t);
        pager = getPager(countAll,pager);
        return e.loadAll(t, pager);
    }

    @Override
    public long count(T t) {
        return e.count(t);
    }

    @Override
    public Pager getPager(long countAll, Pager pager){
        //总数
        pager.setItemCount(countAll);
        //判断一共的页数
        long res = countAll / pager.getPageSize();
        if (res * pager.getPageSize() < countAll)
            pager.setPageCount(++res);
        else
            pager.setPageCount(res);
        //判断是否为首页
        if (pager.getPageIndex() <= 1) {
            pager.setPageIndex(1);
            pager.setFirst(true);
        } else
            pager.setFirst(false);
        //判断是否为末页
        if (pager.getPageIndex() >= res) {
            pager.setLast(true);
            pager.setPageIndex(res);
        } else
            pager.setLast(false);
        //
        if (pager.getPageIndex()==0){
            pager.setPageIndex(1);
        }
        pager.setBeginIndex((pager.getPageIndex() - 1) * pager.getPageSize());
        System.out.println("countAll = [" + countAll + "], pager = [" + pager + "]");
        return pager;
    }



}
