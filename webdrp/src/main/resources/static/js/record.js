$("#noData").hide()
// 分页部分
var pageIndex = 1
var end = false
function getListData() {
    if (loading) {
        return;
    }
    loading = true
    if (end){
        return
    }
    console.log("getData1111")
    $.get(ctxPath + "wechat/api/recordList", { pageIndex: pageIndex},function(data){
        pageIndex++;
        if (data.status){
            for(var i = 0; i < data.data.list.length; i++) {
                var item = data.data.list[i];
                $("#incomeList").append('<div class="incomeCard">' +
                    '<div class="incomeCardCell">' +
                    '<div class="incomeItem incomePrice">' +
                    '<font class="price">'+item.money+'元</font>' +
                    '</div>' +
                    '</div>' +
                    '<div class="recordCardCell">' +
                    '<div class="recordItem">' +
                    '<p>状&nbsp;&nbsp;&nbsp;态</p>' +
                    '</div>' +
                    '<div class="recordItemData">' +
                    '<p class="colorGreen">'+item.statusString+'</p>' +
                    '</div>' +
                    '</div>' +
                    '<div class="recordCardCell">' +
                    '<div class="recordItem">' +
                    '<p>感恩奖</p>' +
                    '</div>' +
                    '<div class="recordItemData">' +
                    '<p>'+item.poundage+'</p>' +
                    '</div>' +
                    '</div>' +
                    '<div class="recordCardCell">' +
                    '<div class="recordItem">' +
                    '<p>姓&nbsp;&nbsp;&nbsp;名</p>' +
                    '</div>' +
                    '<div class="recordItemData">' +
                    '<p>'+item.zfbName+'</p>' +
                    '</div>' +
                    '</div>' +
                    '<div class="recordCardCell">' +
                    '<div class="recordItem">' +
                    '<p>支付宝帐号</p>' +
                    '</div>' +
                    '<div class="recordItemData">' +
                    '<p>'+item.zfb+'</p>' +
                    '</div>' +
                    '</div>' +
                    '<div class="recordCardCell">' +
                    '<div class="recordItem">' +
                    '<p>发起时间</p>' +
                    '</div>' +
                    '<div class="recordItemData">' +
                    '<span>'+item.createTime+'</span>' +
                    '</div>' +
                    '</div>' +
                    '</div>');
                loading = false;

            }
            end = data.data.pager.last
            // 假如一个没有，提示什么

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