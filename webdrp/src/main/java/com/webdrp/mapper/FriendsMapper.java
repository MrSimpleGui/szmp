package com.webdrp.mapper;

import com.webdrp.common.Pager;
import com.webdrp.entity.Friends;

import com.webdrp.entity.dto.FriendsDto;
import com.webdrp.entity.vo.FriendsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FriendsMapper {

    void insert(Friends friends);

    void delete(Friends friends);

    Friends findById(Friends friends);

    void addFriendsImage(@Param("friendsId") Integer friendsId,@Param("imageId") Integer imageId);

    void deleteCommodityImageByCommodityId(Friends friends);

    long loadAllCount(FriendsDto friendsDto);

    List<FriendsVo> loadAll(@Param("entity") FriendsDto friendsDto, @Param("pager")Pager pager);
}
