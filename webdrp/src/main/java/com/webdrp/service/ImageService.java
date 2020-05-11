package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.Image;

import java.util.List;

/**
 * Created by yuanming on 2018/8/10.
 */
public interface ImageService extends BaseService<Image>{

    //通过增加key-value表来做
    //获取菜单图片
    List<Image>  findMenuImage();

    void setMenuImage(Integer oldImageId,Integer newImageId);

    //获取轮播图片  key为  wechatswip   多少张让前端所的算
    /**
     * @param count 多少张
     * @return
     */
    List<Image> findSwipterImage(int count);

    /**
     * 设置轮播图
     * @param oldImageId  旧的图片ID
     * @param newImageId  新的图片Id
     * @return
     */
    List<Image> setSwipterImage(Integer oldImageId,Integer newImageId);

    //获取个人中心背景图  wechatpersonalcenter
    Image findPersonalBackground();

    void setPersonalBackground(Image Image);

}
