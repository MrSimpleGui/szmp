// banner
//ranisition effect. Could be "slide", "fade", "cube", "coverflow" or "flip"
$(".swiper-container").swiper({
	loop: true,
	spaceBetween: 50,
	effect: 'coverflow',
	autoplay: 5000
});

//打开套餐选择按钮
$("#selectPackage").click(function() {
	$("#packages").popup();
	$("#tabbarBt").toggle()
	console.log("open popup!!!")
})

//关闭按钮
$("#closePackageSelect").click(function() {
	$.closePopup()
	console.log("close popup!!!")
	$("#tabbarBt").toggle()
})
//防止点鸡蒙版
$(".weui-popup__overlay").click(function() {
	$("#tabbarBt").toggle();
})
// 蒙版点击
$("#selectPackageToPay").click(function() {
	var packageId = $('input:radio:checked').val()
	var buyCount = $("#buyCount").val()
	// $.toast("packageId =" + packageId + "buyCount="+buyCount, "text");
	window.location.href = ctxPath + "wechat/h5/prepay?packageId="+packageId+"&count="+buyCount;
})

//数字选择器部分

var MAX = 99,
	MIN = 1;
$('.weui-count__decrease').click(function(e) {
	var $input = $(e.currentTarget).parent().find('.weui-count__number');
	var number = parseInt($input.val() || "0") - 1
	if(number < MIN) number = MIN;
	$input.val(number)
})
$('.weui-count__increase').click(function(e) {
	var $input = $(e.currentTarget).parent().find('.weui-count__number');
	var number = parseInt($input.val() || "0") + 1
	if(number > MAX) number = MAX;
	$input.val(number)
})

$('.delete-swipeout').click(function() {
	$(this).parents('.weui-cell').remove()
})
$('.close-swipeout').click(function() {
	$(this).parents('.weui-cell').swipeout('close')
})

//数字选择器部分end

//加入购物车
$("#selectPackageToCollect").click(function() {
	var packageId = $('input:radio:checked').val()
	var buyCount = $("#buyCount").val()
	$.post(ctxPath+ 'wechat/api/addCart', {packageId: packageId,count: buyCount},
		function(data) {
			if (data.status) {
				console.log("获取数据正常")
				console.log(data)
				$.toast.prototype.defaults.duration = 1000
				$.toast('加入购物车成功', "text");
			} else {
				$.toast(data.errorMsg, "text");
			}

		},"json");
})