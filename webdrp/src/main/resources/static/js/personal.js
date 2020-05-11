$(".wallet-number").each(function(){
    var dataValue = $(this).text();
    var arr2 = dataValue.split(".")
    var html = '';
    arr2.forEach(function (value,index) {

        if (index == 0){
            html = value + ".";
        }else{
            html += '<font class="data-float">'+value+'</font>';
        }

    });
    $(this).html(html);
});