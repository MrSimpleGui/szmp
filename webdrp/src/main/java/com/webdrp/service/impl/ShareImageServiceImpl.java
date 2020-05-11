package com.webdrp.service.impl;

import com.webdrp.common.BaseServiceImpl;
import com.webdrp.entity.ShareImage;
import com.webdrp.mapper.ShareImageMapper;
import com.webdrp.service.ShareImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhang yuan ming
 * @Date: create in 17:58 2020-03-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */

@Service
public class ShareImageServiceImpl extends BaseServiceImpl<ShareImage, ShareImageMapper> implements ShareImageService {

    @Autowired
    ShareImageMapper shareImageMapper;

    @Override
    public ShareImage findByMemberIdAndCommodityId(ShareImage shareImage) {
        return shareImageMapper.findByMemberIdAndCommodityIdOnly(shareImage);
    }

    @Override
    public void clearAllQrCode() {
        shareImageMapper.clearAllQrCode();

    }

    @Override
    public void clearOneQrCode(Integer id) {
        shareImageMapper.clearOneQrCode(id);
    }
}
