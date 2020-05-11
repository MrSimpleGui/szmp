
// 分页部分
var pageIndex = 1
var end = false
function getListData() {
    if (loading) {
        return;
    }
    if (end){
        return
    }
    console.log("getData1111")
    $.get(ctxPath + "wechat/api/notificationList", { pageIndex: pageIndex},function(data){
        pageIndex++;
        if (data.status){
            console.log("获取数据正常")
            console.log(data)
            for(var i = 0; i < data.data.list.length; i++) {
                var item = data.data.list[i];
                // console.log(item);
                var detailUrl =ctxPath + "wechat/h5/topicDetail?id="+item.id;
                $("#notificationList").append(''+
                    '<div class="topic-item">'+
                    '<a href="'+detailUrl+'">'+
                    '<p>'+item.author+'<span class="topic-date">'+item.showdate+'</span></p>'+
                    '<p>'+item.title+'</p>'+
                    '</a>'+
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