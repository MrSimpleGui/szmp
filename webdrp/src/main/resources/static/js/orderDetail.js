$("#addressChange").click(function() {
	// 隐藏脱离dom部分
	$("#tabbarBt2").toggle();
	$("#addresPickPage").popup();
})

// 地址选择
$("#city-picker").cityPicker({
	title: "请选择收货地址",
	onChange: function(picker, values, displayValues) {
		console.log(values, displayValues);
	}
});

// 使用旧地址按钮
$("#useHisAddress").click(function(){
	$("#tabbarBt2").toggle();
})

//确认按钮
$("#confirmCity").click(function(){
	// 获取城市地址
	var newTel = $("#newTel").val();
	if ("" == newTel){
		$.toast("请输入手机号码", "text");
		$("#newTel").focus();
		return;
	}
	if (newTel.length != 11){
		$.toast("手机号码格式错误，请重新输入", "text");
		$("#newTel").focus();
		return;
	}
	var newName = $("#newName").val();
	if ("" == newName){
		$.toast("请输入收货人名字", "text");
		$("#newName").focus()
		return;
	}
	
	var provinceCity = $("#city-picker").val();
	
	var addressDetail = $("#addresDetail").val();
	if (""==addressDetail){
		$.toast("请输入详细地址", "text");
		$("#addresDetail").focus()
		return;
	}
	
	if (addressDetail.length <=5){
		$.toast("详细地址不能少于5个字符", "text");
		$("#addresDetail").focus()
		return;
	}
	
	// $.alert("tel="+ newTel +"name="+ newName +"provinceCity="+ provinceCity +"addressDetail="+ addressDetail);
	
	$("#takeName").text(newName);
	$("#takeTel").text(newTel)
	$("#takeAddress").text(provinceCity + addressDetail)
	
	
	$.closePopup();
	$("#tabbarBt2").toggle();
})


// H5调起支付
function onBridgeReady(appId,timeStamp,nonceStr,package,signType,paySign){
	WeixinJSBridge.invoke(
		'getBrandWCPayRequest', {
			"appId":appId,         //公众号ID，由商户传入
			"timeStamp":timeStamp, //时间戳，自1970年以来的秒数
			"nonceStr":nonceStr,   //随机串
			"package":package,
			"signType":signType,   //微信签名方式：
			"paySign":paySign      //微信签名
		},
		function(res){
			if(res.err_msg == "get_brand_wcpay_request:ok" ){
				$.toast("支付成功！", "text");
				location.reload()
				return
			}
			if(res.err_msg == "get_brand_wcpay_request:cancel" ){
				$.toast("支付取消！", "text");
			}
			if(res.err_msg == "get_brand_wcpay_request:fail" ){
				$.toast("支付失败！", "text");
			}
		});
}

$("#continueToPay").click(function () {
	$.post(ctxPath+"wechat/api/continueToPay", {"id":orderId},
		function(data){
			if (data.status){
				var payMsg = data.data.payMsg;
				var orderId = data.data.orderId;
				onBridgeReady(payMsg.appId,payMsg.timeStamp,payMsg.nonceStr,payMsg.package,payMsg.signType,payMsg.paySign);
			} else {
				$.toast(data.errorMsg, "text");
			}
		}, "json");
})

$("#cancelOrder").click(function () {
	$.post(ctxPath+"wechat/api/cancelOrder", {"id":orderId},
		function(data){
			if (data.status){
				$.toast("取消成功", "text");
				location.reload();
				return;
			} else {
				$.toast(data.errorMsg, "text");
				return;
			}
		}, "json");
})