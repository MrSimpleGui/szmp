
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
    $.get(ctxPath + "wechat/api/incomeList", { pageIndex: pageIndex},function(data){
        pageIndex++;
        if (data.status){
            console.log("获取数据正常")
            console.log(data)
            for(var i = 0; i < data.data.list.length; i++) {
                var item = data.data.list[i];
                $("#incomeList").append('<div class="incomeCard">' +
                    '<div class="incomeCardCell">' +
                    '<div class="incomeItem incomeDate">' +
                    item.createTime +
                    '</div>' +
                    '</div>' +
                    '<div class="incomeCardCell">' +
                    '<div class="incomeItem incomePrice">' +
                    '<font class="price-unit">+</font>' +
                    '<font class="price">'  +item.money+ '</font>' +
                    '</div>' +
                    '</div>' +
                    '<div class="incomeCardCell">' +
                    '<div class="incomeItem text-center incomeFrom">' +
                    '<p>来源</p>' +
                    '</div>' +
                    '<div class="incomeItem  text-center incomeFrom">' +
                    '<p>'+item.imcomeTypeString+'</p>' +
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