function goToProductDetail(extra) {
  if (extra === undefined){
    return;
  }
  console.log('1')
  window.location.href = ctxPath + 'wechat/h5/productDetailV1?id=' + extra;
}

function goToTopicDetail(extra) {
  if (extra === undefined){
    return;
  }
  console.log('2')
  window.location.href =  'wechat/h5/topicDetail?id='+extra;
}

function goToDescription() {
  console.log('3')
  window.location.href = ctxPath + 'wechat/h5/company';
}

// 分页部分
var menuPageIndex = 1
var menuEnd = false

function getFirstMenu() {
  $.get(ctxPath + 'wechat/api/findFirstMenu', {
      pageIndex: menuPageIndex,
      pageSize: 8
    },
    function(data) {
      if (data.status) {
        console.log("获取数据正常")
        console.log(data)
        $.each(data.data.list, function(index, value) {
          // 使菜单能根据数量分配width大小
          // console.log(index)
          if (value.type === 0) {
            // 跳转商品
            var mydiv1 = $('<li class="swiperMenu_Li"><a href="onlineShoppingMall.html?pid=' + value.id + '&name=' + value.name +
              '&menuId=' + value.id + '" class="js_grid">' +
              '<div class="weui-grid__icon">' +
              '<img src="' + value.imgUrl + '" alt=""><span class="weui-grid__label">' + value.name + '</span>' +
              '</div>' +
              '</a></li>');
          } else if (value.type === 1) {
            // 跳转通知
            var mydiv1 = $('<li class="swiperMenu_Li"><a href="' + ctxPath + 'wechat/h5/topic' + '?name=' + value.name + '&menuId=' + value
              .id + '" class="js_grid">' +
              '<div class="weui-grid__icon">' +
              '<img src="' + value.imgUrl + '" alt=""><span class="weui-grid__label">' + value.name + '</span>' +
              '</div>' +
              '</a></li>');
          } else if (value.type === 2) {
            // 跳转APP下载
            var mydiv1 = $('<li class="swiperMenu_Li"><a href="' + ctxPath + 'wechat/h5/download' + '?name=' + value.name + '&menuId=' +
              value.id + '" class="js_grid">' +
              '<div class="weui-grid__icon">' +
              '<img src="' + value.imgUrl + '" alt=""><span class="weui-grid__label">' + value.name + '</span>' +
              '</div>' +
              '</a></li>');
          } else if (value.type === 3) {
            // 跳转简介
            var mydiv1 = $('<li class="swiperMenu_Li"><a href="' + ctxPath + 'wechat/h5/company' + '?name=' + value.name + '&menuId=' +
              value.id + '" class="js_grid">' +
              '<div class="weui-grid__icon">' +
              '<img src="' + value.imgUrl + '" alt=""><span class="weui-grid__label">' + value.name + '</span>' +
              '</div>' +
              '</a></li>');
          }
          // console.log(index)
          if (index < 4) {
            $(".swiperMenu").css('height', '23.73vw')
            $(".swiperMenu").addClass("swiperMenu_flex")
          } else {
            $(".swiperMenu").css('height', '47.46vw')
            $(".swiperMenu").removeClass("swiperMenu_flex")
          }
          $('.swiperMenu').append(mydiv1);
        })
      } else {
        $.toast(data.errorMsg, "text");
      }
    },'json');
}
getFirstMenu();


function getIndexBanner() {
  $.get(ctxPath+ 'wechat/api/indexBanner',
    null,
    function(data) {
      if (data.status) {
        console.log("获取数据正常")
        console.log(data)
        bannerList = data.data.bannerList
        //轮播图
        $.each(bannerList, function(index, value) {
          // 首页上层轮播图设置
          if (value.type === 0) {
            if (value.useIn === 0) {
              // 无跳转
              var mydiv1 = $('<div class="swiper-slide"><img src="' + value.url + '" alt=""></div>');
            } else if (value.useIn === 1) {
              // 跳转商品
              var mydiv1 = $('<div class="swiper-slide"><img src="' + value.url + '" onclick="goToProductDetail(' +
                value.extra + ')" alt=""></div>');
            } else if (value.useIn === 2) {
              // 跳转通知
              var mydiv1 = $('<div class="swiper-slide"><img src="' + value.url + '" onclick="goToTopicDetail(' +
                value.extra + ')" alt=""></div>');
            } else if (value.useIn === 3) {
              // 跳转公司简介
              var mydiv1 = $('<div class="swiper-slide"><img src="' + value.url +
                '" onclick="goToDescription()" alt=""></div>');
            }
            $('.swp_1').append(mydiv1);
          } else if (value.type === 1) {
            // 活动图轮播图设置
            if (value.useIn === 0) {
              // 无跳转
              var mydiv1 = $('<div class="swiper-slide"><img class="swiper1Pic" src="' + value.url +
                '" alt=""></div>');
            } else if (value.useIn === 1) {
              // 跳转商品
              var mydiv1 = $('<div class="swiper-slide"><img class="swiper1Pic" src="' + value.url +
                '" onclick="goToProductDetail(' +
                value.extra + ')" alt=""></div>');
            } else if (value.useIn === 2) {
              // 跳转通知
              var mydiv1 = $('<div class="swiper-slide"><img class="swiper1Pic" src="' + value.url +
                '" onclick="goToTopicDetail(' +
                value.extra + ')" alt=""></div>');
            } else if (value.useIn === 3) {
              // 跳转公司简介
              var mydiv1 = $('<div class="swiper-slide"><img class="swiper1Pic" src="' + value.url +
                '" onclick="goToDescription()" alt=""></div>');
            }
            $('.swp_2').append(mydiv1);
          }
        })
        $(".swiper-container").swiper({
          loop: true,
          spaceBetween: 50,
          effect: 'coverflow',
          autoplay: 5000
        });
      } else {
        $.toast(data.errorMsg, "text");
      }
    },'json');
}
getIndexBanner();


// 分页部分
var pageIndex = 1
var end = false

function getListData() {
  if (loading) return
  loading = true;

  if (end) return
  $.get(ctxPath + 'wechat/api/findCommodityByMenuId', {
      pageIndex: pageIndex,
      id: 56
    },
    function(data) {
      if (data.data.pager.last === true && data.data.pager.itemCount === 0) {
        console.log('1')
        return;
      } else if (data.data.pager.last === true && data.data.pager.itemCount !== 0 && data.data.pager.pageIndex ===
        pageIndex) {
        console.log('2')
        $.showLoading("加载中");
        pageIndex++;
      } else if (data.data.pager.last === false) {
        console.log('3')
        pageIndex++;
      } else {
        return;
      }
      if (data.status) {
        if (data.data.list.length > 0) {
          for (var i = 0; i < data.data.list.length; i++) {
            var item = data.data.list[i];
            var detailUrl =ctxPath + "wechat/h5/productDetailV1?id="+item.id
            $("#goodsList").append('' +
              '<a class="goods-card" href="' + detailUrl + '">' +
              '<img class="goods-card-img" src="' + item.imgUrl + '"/>' +
              '<p class="goods-card-title">' + item.name + '</p>' +
              '<p class="goods-card-nameItem">' + item.nameItem + '</p>' +
              '<p class="goods-card-price">' +
              '<font class="goods-card-icon">&nbsp;￥</font>' +
              '<font class="sales-price">' + item.price + '</font>' +
              '</p>' +
              '</a>');
            loading = false;
            $.hideLoading()
          }
          // loading = false;
          end = data.data.pager.last
          // 假如没有订单，提示什么
          if (end) {
            $("#noData").show();
            return;
          }
        } else {
          $.hideLoading()
          $("#noData").show();
        }
      } else {
        $.toast(data.errorMsg, "text");
      }
    },'json');
}
getListData();

var loading = false;
$("#weui-loadmore").hide();
$(document.body).infinite(100).on("infinite", function() {
  if (loading) return
  // loading = true;
  // console.log("到底了")
  $("#weui-loadmore").show()
  getListData()
  $("#weui-loadmore").hide();
});
