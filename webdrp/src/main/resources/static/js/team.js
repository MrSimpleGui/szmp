/**
 * 获取团队
 * @param {Object} type
 */

var pageIndex =1
var end = false;
var loading = false;

// page index data 2
var pageIndex2 =1
var end2 = false;
var loading2 = false;

// page data 3
var pageIndex3 =1
var end3 = false
var loading3 = false

/**
 * teamData
 * @param type
 */
function teamList(type){
	if (type==1){
		if (end){
			return
		}
		if (loading){
			return;
		}
		loading = true;
		$("#loadingMore").show();

		$.get(ctxPath + "wechat/api/teamList", { pageIndex: pageIndex,type:type},function(data){
			pageIndex++;
			if (data.status){
				console.log(data)
				for(var i = 0; i < data.data.list.length; i++) {
					var item = data.data.list[i];
					console.log(item)
					$("#teamList1").append('<div class="teamCell">'+
						'<div class="teamCellIcon">'+
						'<img class="teamCellIcon-img" src="'+item.headImg+'" />'+
						'</div>'+
						'<div class="teamUserData">'+
						'<div class="userData">'+
						'<p>'+item.nickname+'</p>'+
						'<p>入驻时间：'+item.createTime+'</p>'+
						'<p>推荐码：'+item.id+'<font>('+gradeToText(item.grade)+')</font></p>'+
						'</div>'+
						'</div>'+
						'</div>')
				}
				end = data.data.pager.last
				if (end){
					$("#noMoreData").show();
					$("#morDataBt").hide();
				}
			}else{
				$.toast(data.errorMsg, "text");
			}
			$("#loadingMore").hide();
			loading = false;
		})

	}
	if (type==2){
		if (end2){
			return
		}
		if (loading2){
			return;
		}
		loading2 = true;
		$("#loadingMore2").show();

		$.get(ctxPath + "wechat/api/teamList", { pageIndex: pageIndex2,type:type},function(data){
			pageIndex2++;
			if (data.status){
				console.log(data)
				for(var i = 0; i < data.data.list.length; i++) {
					var item = data.data.list[i];
					console.log(item)
					$("#teamList2").append('<div class="teamCell">'+
						'<div class="teamCellIcon">'+
						'<img class="teamCellIcon-img" src="'+item.headImg+'" />'+
						'</div>'+
						'<div class="teamUserData">'+
						'<div class="userData">'+
						'<p>'+item.nickname+'</p>'+
						'<p>入驻时间：'+item.createTime+'</p>'+
						'<p>推荐码：'+item.id+'<font>('+gradeToText(item.grade)+')</font></p>'+
						'</div>'+
						'</div>'+
						'</div>')
				}
				end2 = data.data.pager.last
				if (end2){
					$("#noMoreData2").show();
					$("#morDataBt2").hide();
				}
			}else{
				$.toast(data.errorMsg, "text");
			}
			$("#loadingMore2").hide();
			loading2 = false;
		})
	}
	if (type==4){
		if (end3){
			return
		}
		if (loading3){
			return;
		}
		loading3 = true;
		$("#loadingMore3").show();

		$.get(ctxPath + "wechat/api/teamList", { pageIndex: pageIndex3,type:type},function(data){
			pageIndex3++;
			if (data.status){
				console.log(data)
				for(var i = 0; i < data.data.list.length; i++) {
					var item = data.data.list[i];
					console.log(item)
					$("#teamList3").append('<div class="teamCell">'+
						'<div class="teamCellIcon">'+
						'<img class="teamCellIcon-img" src="'+item.headImg+'" />'+
						'</div>'+
						'<div class="teamUserData">'+
						'<div class="userData">'+
						'<p>'+item.nickname+'</p>'+
						'<p>入驻时间：'+item.createTime+'</p>'+
						'<p>推荐码：'+item.id+'<font>('+gradeToText(item.grade)+')</font></p>'+
						'</div>'+
						'</div>'+
						'</div>')
				}
				end3 = data.data.pager.last
				if (end3){
					$("#noMoreData3").show();
					$("#morDataBt3").hide();
				}
			}else{
				$.toast(data.errorMsg, "text");
			}
			$("#loadingMore3").hide();
			loading3 = false;
		})
	}
}

/**
 *
 * @param grade
 * @returns {string}
 */
function gradeToText(grade) {
	if (grade==0){
		return '粉丝'
	}
	if (grade==1){
		return '会员'
	}
	if (grade==2){
		return '经理'
	}
	if (grade==3){
		return '合伙人'
	}
}

teamList(1)
teamList(2)
teamList(4)
