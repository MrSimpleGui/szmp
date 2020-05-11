$(function() {
  // 分页部分
  var pageIndex = 1
  var end = false

  function getListData() {
    $.get(ctxPath +'wechat/api/cartList', {
        pageIndex: pageIndex
      },
      function(data) {
        if (data.data.pager.last === true && data.data.pager.itemCount === 0) {
          console.log('1')
          $('.orderStatus').css('display', 'block')
          return;
        } else if (data.data.pager.last === true && data.data.pager.itemCount !== 0 && data.data.pager.pageIndex ===
          pageIndex) {
          console.log('2')
          $.showLoading("加载中");
          $('.orderStatus').css('display', 'none')
          $("#noData").show();
          pageIndex++;
          // 当有数据拉出时，全选按钮恢复
          $("#quan").attr("index", "0")
          $("#quan").attr("src", ctxPath + "img/icon/unCheck.png")
        } else if (data.data.pager.last === false) {
          console.log('3')
          pageIndex++;
          $('.orderStatus').css('display', 'none')
        } else {
          return;
        }

        if (data.status) {
          console.log("获取数据正常")
          console.log(data)
          for (var i = 0; i < data.data.list.length; i++) {
            var item = data.data.list[i];
            var mydiv1 = $(
              '<li class="shop_list" id="' + item.id +
              '"><div class="checked"><img class="checks" id="unCheck" src="'+ctxPath +'img/icon/unCheck.png" style="width:5.86vw;height:5.86vw;"></img></div>' +
              '<div class="img"><img src="' + item.imageUrl +
              '"></div><div class="content"><div class="content_top"><div class="text"><div class="tit"><p>' +
              item.name + '</p>' +
              '<div></div></div><div class="down">' + item.commodityStyleName + '</div>' +
              '<div class="price"><div class="price_left"><span>￥</span><span class="mon">' + item.price +
              '</span></div>' +
              '<div class="price_right"><div class="mrnus">-</div><div class="cont">' + item.inventory +
              '</div><div class="add">+</div></div></div></div></div></li>'
            )
            $('.main_shop').append(mydiv1);
            loading = false;
            $.hideLoading()
          }
          loading = false;
          end = data.data.pager.last
          // 假如没有订单，提示什么
          if (end) {
            $("#noData").show();
            return;
          }
        } else {
          $.toast(data.errorMsg, "text");
        }
      });
  }

  getListData();
  var loading = false;
  $("#weui-loadmore").hide();
  $(document.body).infinite(10).on("infinite", function() {
    if (loading) return
    loading = true;
    $("#weui-loadmore").show()
    getListData()
    $("#weui-loadmore").hide();
  });

  // 编辑功能
  $(".gohome").click(function() {
    console.log('1')
    gohome()
  })

  // 删除功能
  $(".btn1").click(function() {
    var listLength = $(".main_shop li").length
    var checksIdArr = [] //数组存放值
    for (var i = 0; i < listLength; i++) {
      // 获取购物车每一行的ID,并存到数组
      var checksId = $(".main_shop li")[i].childNodes[0].childNodes[0]
      console.log(checksId.id)
      if (checksId.id === "isCheck") {
        console.log(i, checksId.id)
        checksIdArr.push($(".main_shop li")[i].id);
      }
    }
    console.log(checksIdArr)
    console.log('删除功能')
    delCart(checksIdArr)
  })

  // 去支付功能
  $(".btn").click(function() {
    console.log('去支付功能')
    var listLength = $(".main_shop li").length
    var checksIdArr = [] //数组存放值
    for (var i = 0; i < listLength; i++) {
      // 获取购物车每一行的ID,并存到数组
      var checksId = $(".main_shop li")[i].childNodes[0].childNodes[0]
      console.log(checksId.id)
      if (checksId.id === "isCheck") {
        console.log(i, checksId.id)
        checksIdArr.push($(".main_shop li")[i].id);
      }
    }
    // console.log(checksIdArr)
    var ids = getTextByJs(checksIdArr)
    // console.log(ids)
    window.location.href = ctxPath +"wechat/h5/prepayCart?ids=" + ids;
  })

  // 减少数量按钮
  $(document).on("click", ".mrnus", function() {
    shopAdd($(this));
    che();
  })

  // 增加数量按钮
  $(document).on("click", ".add", function() {
    shopAdd($(this));
    che();
  })

  // 选择按钮
  $(document).on("click", ".checks", function() {
    // console.log($(this))
    checked($(this));
    che();
  })

  //全选按钮
  $(document).on("click", "#quan", function() {
    quan();
  })


  function gohome() {
    if ($(".gohome").attr("index") == 0) {
      // 当点击编辑时
      $(".gohome").text("完成")
      $(".gohome").attr("index", "1")
      $(".te").css({
        "display": "none"
      })
      $(".btn").css({
        "display": "none"
      })
      $(".btn1").css({
        "display": "block"
      })
    } else {
      // 当不点击编辑时
      $(".gohome").text("编辑")
      $(".gohome").attr("index", "0")
      $(".te").css({
        "display": "block"
      })
      $(".btn").css({
        "display": "block"
      })
      $(".btn1").css({
        "display": "none"
      })
    }
  }

  //解决金额小数点问题
  function numberFix(num) {
    return Math.round(num * 100) / 100
  }

  //数组拆分拼接字符串方法
  function getTextByJs(arr) {
    var str = "";
    for (var i = 0; i < arr.length; i++) {
      // console.log(arr[i])
      str += arr[i] + ",";
    }
    //去掉最后一个逗号(如果不需要去掉，就不用写)
    if (str.length > 0) {
      str = str.substr(0, str.length - 1);
    }
    return str;
  }

  // 删除购物车方法
  function delCart(val) {
    if (val.length !== 0) {
      $.confirm("确定将这" + val.length + "个宝贝删除？", function() {
        //点击确认后的回调函数
        var allId = getTextByJs(val)
        $.post(ctxPath +'wechat/api/delCart', {
            cartId: allId
          },
          function(data) {
            if (data.status) {
              console.log("获取数据正常")
              console.log(data)
              $.toast('删除成功', "text");
              setTimeout(function() {
                location.reload();
              }, 1000);
            } else {
              $.toast(data.errorMsg, "text");
            }
      
          },'json');
      }, function() {
        //点击取消后的回调函数
      });
    } else {
      $.toast('您未选择要删除的宝贝', "text");
    }
  }

  // 加减购物车商品数量更新接口
  function updateBuyCount(val1, val2) {
    $.post(ctxPath + 'wechat/api/updateBuyCount', {
        "id": val1,
        'count': val2
      }, "json",
      function(data) {
        if (data.status) {
          // console.log("获取数据正常")
          // console.log(data)
        } else {
          $.toast(data.errorMsg, "text");
        }
      });
  }

  //增加数量方法
  function shopAdd(obj) {
    let commodityStyleId = obj.parents('.shop_list')[0].id
    var domCheckStatus = obj[0].parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.children[0].children[
      0].id
    if (obj.html() == "+") {
      // 商品款式ID
      // console.log(obj.prev().html())
      let num = parseInt(obj.prev().html());
      // 商品款式数量
      let commodityStyleCount = num + 1
      //更新购买数量接口
      updateBuyCount(commodityStyleId, commodityStyleCount)
      num++;
      if (num !== 0) {
        obj.prev().html(num);
      } else {
        $.toast.prototype.defaults.duration = 1000
        $.toast('该宝贝不能减少了哦~', "text");
      }

      // 改变价格
      // 判断是否选中
      if (domCheckStatus === "isCheck") {
        let pricr = numberFix(obj.parent().prev().find(".mon").html());
        // console.log(pricr);
        let money = numberFix($("#money").html());
        money += pricr;
        // console.log(price)
        $("#money").html(numberFix(money));
      }
    } else if (obj.html() == "-") {
      // 商品款式ID
      let commodityStyleId = obj.parents('.shop_list')[0].id
      let num = parseInt(obj.next().html());
      // 商品款式数量
      let commodityStyleCount = num - 1
      //更新购买数量接口
      updateBuyCount(commodityStyleId, commodityStyleCount)
      num--;
      if (num <= 0) {
        num = 0;
        // return;
      }
      if (num !== 0) {
        obj.next().html(num);
      } else {
        $.toast.prototype.defaults.duration = 1000
        $.toast('该宝贝不能减少了哦~', "text");
      }

      // 改变价格
      // 判断是否选中
      if (domCheckStatus === "isCheck") {
        let price2 = numberFix(obj.parent().prev().find(".mon").html());
        // console.log(price2);
        let money = numberFix($("#money").html());
        // console.log(commodityStyleCount)
        if (commodityStyleCount !== 0) {
          money -= price2;
          $("#money").html(numberFix(money));
        }
      }
    }
  }

  // 选择(单选)
  function checked(checks) {
    // console.log(checks.html())
    if (checks[0].id === "isCheck") {
      // console.log('12312')
      // 判断全选按钮处于什么状态
      if ($("#quan").attr("index") === "1") {
        $("#quan").attr("index", "0")
        $("#quan").attr("src", ctxPath + "img/icon/unCheck.png")
      }
      var checkId = checks.id
      checks.attr('id', 'unCheck');
      checks.attr("src",ctxPath + "img/icon/unCheck.png")
      var LiAllCount = $(".main_shop").children()
      var checkNum = 0
      for (var i = 0; i < LiAllCount.length; i++) {
        if (LiAllCount[i].childNodes[0].childNodes[0].id === "isCheck") {
          checkNum = checkNum + 1
        }
      }
      $(".btn span").html(checkNum)
      $(".btn1 span").html(checkNum)
      money(checks, 1);
    } else {
      // console.log('112211')
      checks.attr('id', 'isCheck');
      checks.attr("src", ctxPath +  "img/icon/isCheck.png")
      var LiAllCount = $(".main_shop").children()
      var checkNum = 0
      for (var i = 0; i < LiAllCount.length; i++) {
        if (LiAllCount[i].childNodes[0].childNodes[0].id === "isCheck") {
          checkNum = checkNum + 1
        }
      }
      //若 符合全选 则 将 选择全选按钮
      if (checkNum === LiAllCount.length) {
        $("#quan").attr("index", "1")
        $("#quan").attr("src", ctxPath + "img/icon/isCheck.png")
      }
      $(".btn span").html(checkNum)
      $(".btn1 span").html(checkNum)
      money(checks);
    }
  }

  // 选择(全选)
  function allChecked(checks) {
    // console.log(checks[0].id)
    if (checks[0].id === "isCheck") {
      // console.log('12312')
      var checkId = checks.id
      checks.attr('id', 'isCheck');
      checks.attr("src", ctxPath + "img/icon/isCheck.png")
      var LiAllCount = $(".main_shop").children()
      var checkNum = 0
      for (var i = 0; i < LiAllCount.length; i++) {
        if (LiAllCount[i].childNodes[0].childNodes[0].id === "isCheck") {
          checkNum = checkNum + 1
        }
      }
      $(".btn span").html(checkNum)
      $(".btn1 span").html(checkNum)
      allMoney(checks);
    } else {
      // console.log('112211')
      checks.attr('id', 'unCheck');
      checks.attr("src",ctxPath +  "img/icon/unCheck.png")
      var LiAllCount = $(".main_shop").children()
      var checkNum = 0
      for (var i = 0; i < LiAllCount.length; i++) {
        if (LiAllCount[i].childNodes[0].childNodes[0].id === "isCheck") {
          checkNum = checkNum + 1
        }
      }
      $(".btn span").html(checkNum)
      $(".btn1 span").html(checkNum)
      allMoney(checks, 1);
    }
  }

  //计算价格方法
  function money(obj, x) {
    console.log(obj,x)
    // 当前商品价格
    let price = numberFix(obj.parent().parent().find(".mon").html());
    //当前商品选择数量
    let content = parseInt(obj.parent().parent().find(".cont").html());
    // console.log(price,content)
    if (x != null) {
      price *= -1;
    }
    let money = numberFix($("#money").html());
    money += price * content;
    // console.log(price)
    $("#money").html(numberFix(money));
  }

  //计算全部价格
  function allMoney(obj, x) {
    // $("#money").html(0);
    // console.log(obj,x)
    // 当前商品价格
    let price = numberFix(obj.parent().parent().find(".mon").html());
    //当前商品选择数量
    let content = parseInt(obj.parent().parent().find(".cont").html());
    // console.log(price,content)
    if (x != null) {
      price *= -1;
    }
    let money = numberFix($("#money").html());
    // console.log(money)
    if (price * content !== 0) {
      $("#money").html(0);
      money += price * content;
    } else {
      money = 0
    }
    // console.log(price)
    $("#money").html(numberFix(money));
  }

  function che() {
    let ches = $(".checks");
    // console.log(ches.size())
  }
  
  // 全选相关 -- 置零操作
  function setZero() {
    $(".checks").attr('id', 'unCheck');
    $(".checks").attr("src", ctxPath + "img/icon/unCheck.png")
    $("#quan").attr("index", "0")
    $("#quan").attr("src", ctxPath + "img/icon/unCheck.png")
    $(".btn span").html("0")
    $(".btn1 span").html("0")
    $("#money").html('0');
  }
  
  //全选方法
  function quan() {
    if ($("#quan").attr("index") == 0) {
      setZero()
      // 当未全选时,按全选了
      $(".checks").attr('id', 'isCheck');
      $(".checks").attr("src", ctxPath + "img/icon/isCheck.png")
      $("#quan").attr("index", "1")
      $("#quan").attr("src", ctxPath + "img/icon/isCheck.png")
      var listLength = $(".main_shop li").length
      $(".btn span").html(listLength)
      //计算全选后的价格
      for (var i = 0; i < listLength; i++) {
        let dom = $(".main_shop li")[i].children[0].children[0]
        // 将普通的dom节点转化为jquery节点，方便allChecked方法调用
        let domToJquery = $(dom)
        // console.log($(dom))
        allChecked(domToJquery)
      }
    } else {
      // 当全选时，再按全选
      setZero()
    }
  }
})
