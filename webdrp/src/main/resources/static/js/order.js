// 很重要的参数，拿来区分状态当前是哪一个订单状态
whichOne = undefined

//滚动时保存滚动位置
$(window).scroll(function() {
    if ($(document).scrollTop() != 0) {
        // console.log($(document).scrollTop())
        sessionStorage.setItem("offsetTop", $(document).scrollTop());
    } else {
        // console.log($(document).scrollTop())
        sessionStorage.setItem("offsetTop", $(document).scrollTop());
    }
    // 悬浮topTab
    // if ($(document).scrollTop() > 60) {
    //     $('.topTab').addClass("sticky-top")
    //     $('.main_shop').css('margin-top', '30.66vw')
    // } else {
    //     $('.topTab').removeClass("sticky-top")
    //     $('.main_shop').css('margin-top', '4vw')
    // }
});

function getListData(status, orderList) {
    if (end) return
    console.log(status, orderList)
    $.get(ctxPath +'wechat/api/orderListByStatus', {
            pageIndex: pageIndex,
            status: status
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
                pageIndex++;
            } else if (data.data.pager.last === false) {
                console.log('3')
                $('.orderStatus').css('display', 'none')
                pageIndex++;
            } else {
                return;
            }

            if (data.status) {
                console.log("获取数据正常")
                console.log(data)
                if (data.data.list.length > 0) {
                    for (var i = 0; i < data.data.list.length; i++) {
                        var item = data.data.list[i];
                        var detailUrl = ctxPath + "wechat/h5/orderDetail?id="+item.id;

                        // 订单详情 —— 商品
                        var orderDetails = item.orderDetails;
                        var orderDetailsDiv = ''
                        $.each(orderDetails, function(index, value) {
                            orderDetailsDiv = orderDetailsDiv + '<div class="orderDetailList"><img src="' + value.imageUrl +
                                '" class="orderDetailImage"/>' +
                                '<div class="orderDetailInfo"><div class="orderDetailFirstLabel"><div class="orderDetailNameContent"><div class="orderDetailName">' +
                                value.name + '</div>' +
                                '</div><div class="orderDetailPrice">￥' + value.price +
                                '</div></div><div class="orderDetailSecondLabel"><div class="orderDetailNameItem">' + value.nameItem +
                                '</div>' +
                                '<div class="orderDetailCount">x' + value.count + '</div></div></div></div>';
                        })

                        var toPayHtml = ''
                        if (item.status == 0) {
                            var toPayHtml = '<a href="' + detailUrl +
                                '" class="toPayButton">去支付</a>'
                        }

                        $("#orderList" + orderList).append('' +
                            ' <div class="orderCard">' +
                            '<a href="' + detailUrl + '">' +
                            '<div class="orderCell1">' +
                            '<img class="orderIcon" src="'+ctxPath+'img/icon/mall.png" />' +
                            '<div class="orderLabel1">' + item.name + '</div>' +
                            '<div class="labelTitle1">' + item.statusString + '</div></div>' +
                            orderDetailsDiv +
                            '<div class="orderCell2">' +
                            '<div class="orderLabel2">共' + orderDetails.length + '件商品</div>' +
                            '<div class="labelTitle2">' +
                            '<font class="price-unit">合计： ￥</font>' +
                            '<font class="subprice">' + item.subPrice + '</font>' +
                            '</div>' +
                            '</div><div class="orderCell3">' +
                            toPayHtml +
                            '</div>' +
                            '</a>' +
                            '</div>');


                        loading = false;
                        $.hideLoading()
                    }
                    // loading = false;
                    end = data.data.pager.last
                    // 假如没有订单，提示什么
                    if (end) {
                        $("#noData").show();
                        $('.orderStatus').css('display', 'none')
                        return;
                    }
                } else {
                    $.hideLoading()
                    $("#noData").show();
                }
            } else {
                $.toast(data.errorMsg, "text");
            }
        });
    // 模拟数据
}

function touchTab1() {
    whichOne = 1
    console.log("touchTab1")
    // 分页部分
    pageIndex = 1
    end = false
    $(".topTab1").addClass("isChoose");
    $(".topTab2,.topTab3,.topTab4").removeClass("isChoose");
    $(".orderList1").css('display', 'block');
    $(".orderList2,.orderList3,.orderList4").css('display', 'none');
    $("#orderList1,#orderList2,#orderList3,#orderList4").empty()
    getListData(undefined, 1);
    loading = false;
    $("#weui-loadmore").hide();
    $(document.body).infinite(1).on("infinite", function() {
        if (whichOne === 1) {
            if (loading) return
            loading = true;
            // console.log("到底了")
            $("#weui-loadmore").show()
            getListData(undefined, 1);
            $("#weui-loadmore").hide();
        }
    });
}

touchTab1()

function touchTab2() {
    whichOne = 2
    console.log("touchTab2")
    // 分页部分
    pageIndex = 1
    end = false
    $(".topTab2").addClass("isChoose");
    $(".topTab1,.topTab3,.topTab4").removeClass("isChoose");
    $(".orderList2").css('display', 'block');
    $(".orderList1,.orderList3,.orderList4").css('display', 'none');
    $("#orderList1,#orderList2,#orderList3,#orderList4").empty()
    getListData(0, 2);
    loading = false;
    $("#weui-loadmore").hide();
    $(document.body).infinite(1).on("infinite", function() {
        if (whichOne === 2) {
            if (loading) return
            loading = true;
            // console.log("到底了")
            $("#weui-loadmore").show()
            getListData(0, 2);
            $("#weui-loadmore").hide();
        }
    });
}

function touchTab3() {
    whichOne = 3
    console.log("touchTab3")
    // 分页部分
    pageIndex = 1
    end = false
    $(".topTab3").addClass("isChoose");
    $(".topTab2,.topTab1,.topTab4").removeClass("isChoose");
    $(".orderList3").css('display', 'block');
    $(".orderList2,.orderList1,.orderList4").css('display', 'none');
    $("#orderList1,#orderList2,#orderList3,#orderList4").empty()
    getListData(2, 3);
    loading = false;
    $("#weui-loadmore").hide();
    $(document.body).infinite(1).on("infinite", function() {
        if (whichOne === 3) {
            if (loading) return
            loading = true;
            // console.log("到底了")
            $("#weui-loadmore").show()
            getListData(2, 3);
            $("#weui-loadmore").hide();
        }
    });
}

function touchTab4() {
    whichOne = 4
    console.log("touchTab4")
    // 分页部分
    pageIndex = 1
    end = false
    $(".topTab4").addClass("isChoose");
    $(".topTab2,.topTab3,.topTab1").removeClass("isChoose");
    $(".orderList4").css('display', 'block');
    $(".orderList2,.orderList3,.orderList1").css('display', 'none');
    $("#orderList1,#orderList2,#orderList3,#orderList4").empty()
    getListData(3, 4);
    loading = false;
    $("#weui-loadmore").hide();
    $(document.body).infinite(1).on("infinite", function() {
        if (whichOne === 4) {
            if (loading) return
            loading = true;
            // console.log("到底了")
            $("#weui-loadmore").show()
            getListData(3, 4);
            $("#weui-loadmore").hide();
        }
    });
}
