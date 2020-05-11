package com.webdrp.common;


import io.swagger.annotations.ApiModelProperty;

/*import lombok.Data;

@Data*/
public class Pager {
    @ApiModelProperty("第几页")
    private long pageIndex = 1;//页码，第几页
    @ApiModelProperty("一页几条")
    private long pageSize = 10;//一页显示的条数
    @ApiModelProperty("总条数")
    private long itemCount;//总条数
    @ApiModelProperty("总页数")
    private long pageCount;//能分多少页
    @ApiModelProperty("是否为首页")
    private boolean first;//是否第一页
    @ApiModelProperty("是否为最后页")
    private boolean last;//是否最后一页

    private long beginIndex;

    public Pager() {

    }

    private Pager(Builder builder) {
        this.pageIndex = builder.pageIndex;
        this.pageSize = builder.pageSize;
        this.itemCount = builder.itemCount;
        this.pageCount = builder.pageCount;
        this.first = builder.first;
        this.last = builder.last;
        this.beginIndex = builder.beginIndex;
    }

    public static Builder newPager() {
        return new Builder();
    }


    public static final class Builder {
        private long pageIndex = 1;
        private long pageSize = 10;
        private long itemCount;
        private long pageCount;
        private boolean first;
        private boolean last;
        private long beginIndex;

        private Builder() {
        }

        public Pager build() {
            return new Pager(this);
        }

        public Builder pageIndex(long pageIndex) {
            this.pageIndex = pageIndex;
            return this;
        }

        public Builder pageSize(long pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder itemCount(long itemCount) {
            this.itemCount = itemCount;
            return this;
        }

        public Builder pageCount(long pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public Builder first(boolean first) {
            this.first = first;
            return this;
        }

        public Builder last(boolean last) {
            this.last = last;
            return this;
        }

        public Builder beginIndex(long beginIndex) {
            this.beginIndex = beginIndex;
            return this;
        }
    }


    public long getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(long pageIndex) {
        this.pageIndex = pageIndex;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getItemCount() {
        return itemCount;
    }

    public void setItemCount(long itemCount) {
        this.itemCount = itemCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public long getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(long beginIndex) {
        this.beginIndex = beginIndex;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", itemCount=" + itemCount +
                ", pageCount=" + pageCount +
                ", first=" + first +
                ", last=" + last +
                ", beginIndex=" + beginIndex +
                '}';
    }
}
