window.onload = function() {
  $('#cover').width(window.screen.width);
  $('#cover').height(window.screen.height);
  if (isWeiXin()) {
    console.log("是微信端")
    $('#cover').css('display', 'block')

    if (isiOS()){
      $("#secondText").text("②  选择  ' 在Safari中打开 '")
    }


    $(".anroidButton").click(function() {
      console.log("安卓按钮1")
      $('#cover').css('display', 'block')
    })

    $(".iosButton").click(function() {
      console.log("IOS按钮1")
      $('#cover').css('display', 'block')
    })

  } else {
    console.log("不是微信端")

    $(".anroidButton").click(function() {
      console.log("安卓按钮2")
      $('#cover').css('display', 'none')
    })

    $(".iosButton").click(function() {
      console.log("IOS按钮2")
      $('#cover').css('display', 'none')
    })
  }
}

// 判断是否微信端
function isWeiXin() {
  var ua = window.navigator.userAgent.toLowerCase();
  if (ua.match(/MicroMessenger/i) == 'micromessenger') {
    return true;
  } else {
    return false;
  }
}



function isiOS() {
  var u = navigator.userAgent;
  var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
  var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/);     //ios终端
  return isiOS;
}
