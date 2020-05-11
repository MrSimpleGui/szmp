package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.common.Pager;
import com.webdrp.constant.UserIn;
import com.webdrp.entity.Comment;
import com.webdrp.entity.Friends;
import com.webdrp.entity.Image;
import com.webdrp.entity.Like;
import com.webdrp.entity.dto.FriendsDto;

import com.webdrp.entity.vo.FriendsVo;
import com.webdrp.mapper.FriendsMapper;
import com.webdrp.mapper.ImageMapper;
import com.webdrp.service.CommentService;
import com.webdrp.service.FriendsService;
import com.webdrp.service.LikeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendsServiceImpl implements FriendsService {

    @Autowired
    private FriendsMapper friendsMapper;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    /**
     * 发布朋友圈
     *
     * @param friendsDto
     */
    @Override
    public void insert(FriendsDto friendsDto) {
        Friends friends = new Friends();
        BeanUtils.copyProperties(friendsDto, friends);
        friendsMapper.insert(friends);//先插入朋友圈的文字内容
        List<Image> images = friendsDto.getImages();
        for (Image image : images) {
            image.setUseIn(UserIn.OTHER);
            image.beforeCreate();
            imageMapper.insert(image);
            friendsMapper.addFriendsImage(friends.getId(), image.getId());
        }
    }

    @Override
    public void update(Friends friends) {

    }

    @Override
    public void delete(Friends friends) {
        friends.beforeDelete();
        friendsMapper.delete(friends);//先删除文字内容
        friendsMapper.deleteCommodityImageByCommodityId(friends);//删除关联图片
        //先删除评论
        Comment comment = new Comment();
        comment.setFriendsId(friends.getId());
        comment.beforeDelete();
        commentService.deleteCommentByFriendsId(comment);
        //再删除点赞
        Like like = new Like();
        like.setFriendsId(friends.getId());
        like.beforeDelete();
        likeService.deleteByFriendsId(like);
    }


    public Pager getPager(long countAll, Pager pager) {
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
        if (pager.getPageIndex() == 0) {
            pager.setPageIndex(1);
        }
        pager.setBeginIndex((pager.getPageIndex() - 1) * pager.getPageSize());
        System.out.println("countAll = [" + countAll + "], pager = [" + pager + "]");
        return pager;
    }


    public List<FriendsVo> loadAll(Pager pager, FriendsDto friendsDto) {
        long countAll = friendsMapper.loadAllCount(friendsDto);
        pager = getPager(countAll, pager);
        List<FriendsVo> friendsVos = friendsMapper.loadAll(friendsDto, pager);
        return friendsVos;
    }
}
