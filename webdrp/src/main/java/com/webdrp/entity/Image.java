package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanming on 2018/8/10.
 * 系统图片
 */
@Data
public class Image extends BaseBean{
        //图片名称
        @ApiModelProperty("图片价格")
        private String name;
        //图片存储场所 0七牛  1 本地
        @ApiModelProperty("图片存储场所 0七牛  1 本地")
        private Integer imageType;
        //图片链接
        @ApiModelProperty("图片链接")
        private String url;
        //图片全路径链接
        @ApiModelProperty("图片全路径链接")
        private String fullUrl;
        //业务使用场景  0商品  1 其他
        @ApiModelProperty("业务使用场景  0商品  1 其他")
        private Integer useIn;

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Integer getImageType() {
                return imageType;
        }

        public void setImageType(Integer imageType) {
                this.imageType = imageType;
        }

        public String getUrl() {
                return url;
        }

        public void setUrl(String url) {
                this.url = url;
        }

        public String getFullUrl() {
                return fullUrl;
        }

        public void setFullUrl(String fullUrl) {
                this.fullUrl = fullUrl;
        }

        public Integer getUseIn() {
                return useIn;
        }

        public void setUseIn(Integer useIn) {
                this.useIn = useIn;
        }
}
