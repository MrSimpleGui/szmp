package com.webdrp.entity;

import com.webdrp.common.BaseBean;

public class Like extends BaseBean{

    private Integer richUserId;

    private Integer friendsId;


    public Integer getRichUserId() {
        return richUserId;
    }

    public void setRichUserId(Integer richUserId) {
        this.richUserId = richUserId;
    }

    public Integer getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(Integer friendsId) {
        this.friendsId = friendsId;
    }
}
