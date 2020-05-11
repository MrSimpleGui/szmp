package com.webdrp.service;

import com.webdrp.common.BaseService;
import com.webdrp.entity.ShareImage;

/**
 * @Author: zhang yuan ming
 * @Date: create in 17:58 2020-03-23
 * @mail: zh878998515@gmail.com
 * @Description:
 */
public interface ShareImageService extends BaseService<ShareImage> {

    ShareImage findByMemberIdAndCommodityId(ShareImage shareImage);

    void clearAllQrCode();

    void clearOneQrCode(Integer id);
}
