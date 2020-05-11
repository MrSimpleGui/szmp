// banner
//ranisition effect. Could be "slide", "fade", "cube", "coverflow" or "flip"
$(".swiper-container").swiper({
	loop: true,
	spaceBetween: 50,
	effect:'coverflow',
	autoplay: 5000
});

var loading = false;
var loading1 = false;

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
	$.get(ctxPath + "wechat/api/goodsList", { pageIndex: pageIndex,type:1},function(data){
		pageIndex++;
		if (data.status){
			console.log("获取数据正常")
			console.log(data)
			for(var i = 0; i < data.data.list.length; i++) {
				var item = data.data.list[i];
				var detailUrl =ctxPath + "wechat/h5/productDetail?id="+item.id
				$("#goodsList").append('' +
					'<a class="goods-card" href="'+detailUrl+'">'+
					'<img class="goods-card-img" src="'+item.imgUrl+'"/>'+
					'<p class="goods-card-title">'+item.name+'</p>'+
					'<p class="goods-card-price">'+
					'<font class="goods-card-icon">&nbsp;￥</font>'+
					'<font class="sales-price">'+item.commodityStyleVoList[0].price+'</font>'+
					'<font class="bt-buy">购买</font>'+
					'</p>'+
					'</a>');
				loading = false;
			}
			$("#weui-loadmore").hide();

			end = data.data.pager.last
		}else{
			$.toast(data.errorMsg, "text");
		}
	});
}
getListData();



// 分页部分
var pageIndex1 = 1
var end1 = false
function getListData1() {
	if (loading1) {
		return;
	}
	loading1 = true;
	if (end1){
		return
	}
	$.get(ctxPath + "wechat/api/goodsList", { pageIndex: pageIndex,type:2},function(data){
		pageIndex1++;
		if (data.status){
			for(var i = 0; i < data.data.list.length; i++) {
				var item = data.data.list[i];
				var detailUrl =ctxPath + "wechat/h5/productDetail?id="+item.id
				$("#goodsList1").append('' +
					'<a class="goods-card" href="'+detailUrl+'">'+
					'<img class="goods-card-img" src="'+item.imgUrl+'"/>'+
					'<p class="goods-card-title">'+item.name+'</p>'+
					'<p class="goods-card-price">'+
					'<font class="goods-card-icon">&nbsp;￥</font>'+
					'<font class="sales-price">'+item.commodityStyleVoList[0].price+'</font>'+
					'<font class="bt-buy">购买</font>'+
					'</p>'+
					'</a>');
				loading1 = false;
			}
			$("#weui-loadmore").hide();
			end1 = data.data.pager.last
		}else{
			$.toast(data.errorMsg, "text");
		}
	});
}
getListData();
getListData1();



function action(useIn,extra) {
	console.log(useIn)
	console.log(extra)

	if (useIn === 0){
		return
	}

	//公司简介
	if (useIn === 3){
		location.href = ctxPath + 'wechat/h5/company';
		return;
	}

	// 产品
	if (useIn === 1){
		if (extra === undefined){
			return;
		}
		location.href = ctxPath + 'wechat/h5/productDetail?id=' + extra;
		return;
	}
	if (useIn === 2){
		if (extra === undefined){
			location.href = ctxPath + 'wechat/h5/topic';
			return;
		}
		location.href = ctxPath + 'wechat/h5/topicDetail?id='+extra;
		return;
	}
}

// var loading = false;
// $("#weui-loadmore").hide();
// $(document.body).infinite(100).on("infinite", function() {
// 	if(loading)
// 		return;
// 	console.log("到底了")
// 	$("#weui-loadmore").show()
// 	getListData()
// 	$("#weui-loadmore").hide();
// });
