package com.webdrp.service;

import com.webdrp.entity.Like;

public interface LikeService {

    void delete(Like like);

    void insert(Like like);

    void deleteByFriendsId(Like like);


}
