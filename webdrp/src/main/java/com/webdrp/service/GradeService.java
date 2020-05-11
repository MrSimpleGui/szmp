package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.common.Pager;
import com.webdrp.entity.Grade;
import com.webdrp.entity.vo.GradeVo;

import java.util.List;

/**
 * Created by yuanming on 2018/8/22.
 */
public interface GradeService extends BaseService<Grade> {

    void insert(Grade grade);

    Grade findByRank(Integer rank);

    void delete(Grade grade);

    Boolean findGradeByNameAndRank(Grade grade);

    List<GradeVo> loadAllVo(Grade grade, Pager pager);
}
