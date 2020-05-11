
// 分页部分
var pageIndex = 1
var end = false
function getListData() {
    if (loading) {
        return;
    }
    loading = true;
    if (end){
        return
    }
    console.log("getData1111")
    $.get(ctxPath + "wechat/api/orderList", { pageIndex: pageIndex},function(data){
        pageIndex++;
        if (data.status){
            console.log("获取数据正常")
            console.log(data)
            for(var i = 0; i < data.data.list.length; i++) {
                var item = data.data.list[i];
                // console.log(item);
                var detailUrl =ctxPath + "wechat/h5/orderDetail?id="+item.id;
                var toPayHtml = ''
                if (item.status == 0){
                    var toPayHtml = '<a href="'+detailUrl+'" class="weui-btn weui-btn_mini weui-btn_primary" style="background-color:#ff5502;">去支付</a>'
                }
                $("#orderList").append('' +
                   ' <div class="orderCard">'+
                    '<a href="'+detailUrl+'">'+
                    '<div class="orderCell order-buttom-border">'+
                    '<div class="orderLabel orderTitle">订单号</div>'+
                    '<div class="labelTitle orderTitle">'+item.userOrder+'</div>'+
                    '</div>'+
                    '<div class="orderCell">'+
                    '<div class="orderLabel">商品名称</div>'+
                    '<div class="labelTitle goods-card-title ">'+item.name + item.nameItem +'</div>'+
                    '</div>'+
                    '<div class="orderCell">'+
                    '<div class="orderLabel">下单时间</div>'+
                    '<div class="labelTitle">'+item.createTime+'</div>'+
                    '</div>'+
                    '<div class="orderCell">'+
                    '<div class="orderLabel">订单金额</div>'+
                    '<div class="labelTitle">'+
                    '<font class="price-unit">￥</font>'+
                    '<font class="subprice">'+item.subPrice+'</font>'+
                    '</div>'+
                    '</div>'+
                    '<div class="orderCell order-buttom-border">'+
                    '<div class="orderLabel">订单状态</div>'+
                    '<div class="labelTitle">'+
                    item.statusString +
                    '</div>'+
                    '</div>'+
                    '</a>'+
                    '<div class=" text-right" style="margin-top: 2vw;">'+
                     toPayHtml +
                    '</div>'+
                    '</div>');
                loading = false;
            }
            end = data.data.pager.last
            // 假如没有订单，提示什么

            if (end){
               $("#noData").show();
               return;
            }
        }else{
            $.toast(data.errorMsg, "text");
        }
    });
    // 模拟数据

}
getListData();

var loading = false;
$("#weui-loadmore").hide();
$(document.body).infinite(100).on("infinite", function() {
    if(loading)
        return;
    console.log("到底了")
    $("#weui-loadmore").show()
    getListData()
    $("#weui-loadmore").hide();
});