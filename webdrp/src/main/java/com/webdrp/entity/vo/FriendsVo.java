package com.webdrp.entity.vo;

import com.webdrp.entity.Friends;
import com.webdrp.entity.Image;
import com.webdrp.entity.Like;

import java.util.List;

public class FriendsVo extends Friends{

    private String userName;

    private String nickName;

    private List<LikeVo> likeVos;//点赞

    private List<CommentVo> commentVos;//评论

    private List<Image> images;

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<LikeVo> getLikeVos() {
        return likeVos;
    }

    public void setLikeVos(List<LikeVo> likeVos) {
        this.likeVos = likeVos;
    }

    public List<CommentVo> getCommentVos() {
        return commentVos;
    }

    public void setCommentVos(List<CommentVo> commentVos) {
        this.commentVos = commentVos;
    }
}
