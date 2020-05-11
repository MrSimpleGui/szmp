package com.webdrp.service;

import com.webdrp.common.Pager;
import com.webdrp.entity.Friends;
import com.webdrp.entity.dto.FriendsDto;
import com.webdrp.entity.vo.FriendsVo;

import java.util.List;

public interface FriendsService {


    void insert(FriendsDto friendsDto);


    void update(Friends friends);


    void delete(Friends friends);

    List<FriendsVo> loadAll(Pager pager, FriendsDto friendsDto);
}
