package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.Grade;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yuanming on 2018/8/22.
 */
@Component
public interface GradeMapper extends BaseMapper<Grade> {

    void insert(Grade grade);

//    List<Grade> findAll();

    Grade findByRank(Integer rank);

    String findGradeNameByGradeRank(Integer rank);

    List<Grade> findGradeByNameAndRank(Grade grade);

}
