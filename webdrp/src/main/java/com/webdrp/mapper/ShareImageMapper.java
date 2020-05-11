package com.webdrp.mapper;

import com.webdrp.common.BaseMapper;
import com.webdrp.entity.ShareImage;

/**
 * @Author: zhang yuan ming
 * @Date: create in 17:45 2020-03-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public interface ShareImageMapper extends BaseMapper<ShareImage> {

    /**
     * 查询用户在这个商品下的分享图片
     * @param shareImage
     * @return
     */
    ShareImage findByMemberIdAndCommodityIdOnly(ShareImage shareImage);

    void clearAllQrCode();

    void clearOneQrCode(Integer id);
}
