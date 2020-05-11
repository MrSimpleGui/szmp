package com.webdrp.service.impl;


import com.webdrp.entity.Like;
import com.webdrp.mapper.LikeMapper;
import com.webdrp.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {
    @Autowired
    private LikeMapper likeMapper;


    /**
     * 取消点赞
     * @param like
     */
    @Override
    public void delete(Like like) {
        like.beforeDelete();
        likeMapper.delete(like);
    }

    /**
     * 点赞
     * @param like
     */
    @Override
    public void insert(Like like) {
        Like res = likeMapper.findByFriendsIdAndRichUserId(like);
        if (Objects.isNull(res)) {
            likeMapper.insert(like);
        }

    }

    /**
     * 删除某条朋友圈下的点赞
     * @param like
     */
    @Override
    public void deleteByFriendsId(Like like) {
        likeMapper.deleteByFriendsId(like);
    }



}
