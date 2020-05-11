package com.webdrp.service.impl;

import com.webdrp.common.BaseMapper;
import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.Image;
import com.webdrp.mapper.ImageMapper;
import com.webdrp.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuanming on 2018/8/10.
 */
@Service
public class ImageServiceImpl extends BaseServiceImpl<Image,ImageMapper> implements ImageService{

    @Autowired
    ImageMapper imageMapper;

    @Override
    public List<Image> findMenuImage() {
        return null;
    }

    @Override
    public void setMenuImage(Integer oldImageId, Integer newImageId) {

    }

    /**
     * @param count 多少张
     * @return
     */
    @Override
    public List<Image> findSwipterImage(int count) {
        return null;
    }

    /**
     * 设置轮播图
     * @param oldImageId 旧的图片ID
     * @param newImageId 新的图片Id
     * @return
     */
    @Override
    public List<Image> setSwipterImage(Integer oldImageId, Integer newImageId) {
        return null;
    }

    @Override
    public Image findPersonalBackground() {
        return null;
    }

    @Override
    public void setPersonalBackground(Image Image) {

    }

}
