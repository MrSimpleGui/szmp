package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.Pager;
import com.webdrp.entity.Menu;
import com.webdrp.entity.vo.MenuVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findFirstMenu(@Param("entity") Menu menu, @Param("pager") Pager pager);

    List<Menu> findSecondMenu(@Param("entity") Menu menu, @Param("pager") Pager pager);

    List<Menu> findMenuByPidWithPublish(@Param("pid")Integer pid, @Param("publish") Integer publish);

    List<Menu> findMenuByPid(Integer pid);

    int deleteBatchByIds(List<Menu> list);

    void publishByMenuIds(@Param("menuIdList") List<Integer> menuIdList, @Param("publish") Integer publish);

    List<Menu> findFunctionMenu();

    List<Menu> findAllFirstMenu(@Param("entity") Menu menu);

    String findNameById(Integer id);
}
