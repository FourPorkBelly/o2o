$(function() {
    var shopId = getQueryString("shopId");
    var userName = '';
    $("a").each(function (i,e) {
        var url = $(e).attr("href");
        $(e).attr("href",url+"?shopId="+shopId);
    })
    function getList() {
        var listUrl = '/shop/listusershopmapsbyshop?pageIndex=1&pageSize=9999&shopId=' + shopId + '&userName=' + userName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userShopMapList = data.userShopMapList;
                var tempHtml = '';
                userShopMapList.map(function (item, index) {
                    tempHtml += ''
                         +      '<div class="row row-usershopcheck">'
                         +          '<div class="col-50">'+ item.userName +'</div>'
                         +          '<div class="col-50">'+ item.point +'</div>'
                         +      '</div>';
                });
                $('.usershopcheck-wrap').html(tempHtml);
            }
        });
    }

    $('#search').on('input', function (e) {
        userName = e.target.value;
        $('.usershopcheck-wrap').empty();
        getList();
    });

    getList();
});