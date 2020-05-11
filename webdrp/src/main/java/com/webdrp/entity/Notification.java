package com.webdrp.entity;

import com.webdrp.common.BaseBean;
import io.swagger.annotations.ApiModelProperty;

public class Notification extends BaseBean {

    @ApiModelProperty("标题，必填")
    private String title;

    @ApiModelProperty("二级标题")
    private String subtitle;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("内容 富文本 必填")
    private String content;

    @ApiModelProperty("显示日期 年月日YYYY-MM-DD  必填")
    private String showdate;

    @ApiModelProperty("发布  1 发布  0未发布  添加默认为未发布")
    private Integer published;

    @ApiModelProperty("阅读次数 这个给他们自己编辑")
    private Integer viewcount;

    @ApiModelProperty("通知类型 0微信首页通知 ")
    private Integer type;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShowdate() {
        return showdate;
    }

    public void setShowdate(String showdate) {
        this.showdate = showdate;
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getViewcount() {
        return viewcount;
    }

    public void setViewcount(Integer viewcount) {
        this.viewcount = viewcount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
