package com.webdrp.mapper;


import com.webdrp.entity.Like;
import org.springframework.stereotype.Component;

@Component
public interface LikeMapper {


    void insert(Like like);

    void delete(Like like);

    void deleteByFriendsId(Like like);

    Like findByFriendsIdAndRichUserId(Like like);
}
