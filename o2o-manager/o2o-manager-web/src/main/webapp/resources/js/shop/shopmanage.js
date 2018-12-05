$(function () {
    var shopId = getQueryString('shopId');
    var shopInfoUrl = "/shop/getshopmanagementinfo?shopId="+shopId;
    $.getJSON(shopInfoUrl,function (data) {
        if(data.redirect){
            window.location.href = data.url;
        }else {
            if(data.shopId!=undefined&&data.shopId!=null){
                shopId = data.shopId;
            }
            /*$(".shopinfo").each(function (i,s) {
                var url = $(s).attr("href");
                $(s).attr("href",url+"?shopId="+shopId);
            })*/

            $("#shopedit").attr('href','/shop/shopedit?shopId='+shopId);
        }
    })
})