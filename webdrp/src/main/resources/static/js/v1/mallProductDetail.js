$(document).ready(function() {
  // 轮播图配置需放在这里才能正常运行
  $(".swiper-container").swiper({
    loop: true,
    spaceBetween: 50,
    effect: 'coverflow',
    autoplay: 5000
  });

  //打开套餐选择按钮1
  $("#selectPackage1").click(function () {
    $(".bt-pay1,.bt-pay2").css('width', '90%')
    $("#packages").popup();
    $("#tabbarBt").toggle()
    $("#selectPackageToCollect").show()
    $("#selectPackageToPay").hide()
    console.log("open popup!!!")
  })

  //打开套餐选择按钮2
  $("#selectPackage2").click(function () {
    $(".bt-pay1,.bt-pay2").css('width', '90%')
    $("#packages").popup();
    $("#tabbarBt").toggle()
    $("#selectPackageToCollect").hide()
    $("#selectPackageToPay").show()
    console.log("open popup!!!")
  })

  $(".firstMarkDownButton,.thirdMarkDownButton").click(function () {
    $("#tabbarBt").hide()
    console.log("open popup!!!")
  })

  $(".secondMarkDownButton").click(function () {
    $("#tabbarBt").hide()
    $(".bt-pay1,.bt-pay2").css('width', '39vw')
    $(".bt-pay1,.bt-pay2").show()
    console.log("open popup!!!")
  })

  //关闭按钮
  $("#closePackageSelect").click(function () {
    $("#selectPackageToCollect").show()
    $("#selectPackageToPay").show()
    $.closePopup()
    console.log("close popup!!!")
    $("#tabbarBt").toggle()
  })

  //点鸡蒙版1
  $("#overlay1,#overlay2,#overlay3").click(function () {
    $.closePopup()
    console.log("close popup!!!")
    $("#tabbarBt").show()
  })

  //点鸡蒙版2
  $("#overlay2").click(function () {
    $.closePopup()
    console.log("close popup!!!")
    $("#tabbarBt").show()
  })

  //弹出层关闭按钮
  $("#clostPopup1,#clostPopup2,#clostPopup3,#clostPopup4").click(function () {
    console.log(1)
    $.closePopup();
    $("#tabbarBt").show()
  })

  // 去支付
  $("#selectPackageToPay").click(function () {
    var packageId = $('input:radio:checked').val()
    var buyCount = $("#buyCount").val()
    // $.toast("packageId =" + packageId + "buyCount="+buyCount, "text");
    // window.location.href = "prepay.html?packageId=" + packageId + "&count=" + buyCount;
    window.location.href = ctxPath + "wechat/h5/prepay?packageId=" + packageId + "&count=" + buyCount;
  })

  //去收藏
  $("#selectPackageToCollect").click(function () {
    var packageId = $('input:radio:checked').val()
    var buyCount = $("#buyCount").val()
    $.post(ctxPath + 'wechat/api/addCart', {
          packageId: packageId,
          count: buyCount
        }, 'json',
        function (data) {
          if (data.status) {
            console.log("获取数据正常")
            console.log(data)
            $.toast.prototype.defaults.duration = 1000
            $.toast('加入购物车成功', "text");
          } else {
            $.toast(data.errorMsg, "text");
          }
        }, 'json');
  })

  //数字选择器部分

  var MAX = 99,
      MIN = 1;
  $('.weui-count__decrease').click(function (e) {
    var $input = $(e.currentTarget).parent().find('.weui-count__number');
    var number = parseInt($input.val() || "0") - 1
    if (number < MIN) number = MIN;
    $input.val(number)
  })
  $('.weui-count__increase').click(function (e) {
    var $input = $(e.currentTarget).parent().find('.weui-count__number');
    var number = parseInt($input.val() || "0") + 1
    if (number > MAX) number = MAX;
    $input.val(number)
  })

  $('.delete-swipeout').click(function () {
    $(this).parents('.weui-cell').remove()
  })
  $('.close-swipeout').click(function () {
    $(this).parents('.weui-cell').swipeout('close')
  })

  //数字选择器部分end

  //分享功能
  $(".shareLink").click(function () {
    $.showLoading("生成分享图片中");
    $.alert("生成图片")
  })

  //关闭分享功能按钮
  $('.backIcon').click(function () {
    $('.canimg,.saoyisaoText,.backgroundImage,#canbox,.backIcon,.cover,.downLoadImage').css({
      'display': 'none'
    })
    $('.swiper-container').css({
      'display': 'block'
    })
  })

  //分享区域蒙版
  $('.cover').click(function () {
    $('.canimg,.saoyisaoText,.backgroundImage,#canbox,.backIcon,.cover,.downLoadImage').css({
      'display': 'none'
    })
    $('.swiper-container').css({
      'display': 'block'
    })
  })

  //下载分享图片

});