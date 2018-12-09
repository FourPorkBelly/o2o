$(function() {
    var awardId = getQueryString('awardId');
    var awardUrl = "/shop/getawardbyid?awardId="+awardId;
    getSearchDivData();
    function getSearchDivData() {
        var url = awardUrl;
        $
            .getJSON(
                url,
                function(data) {
                    if (data.success) {
                        var award = data.award;
                        $('#award-name').text(award.awardName);
                        $('#award-desc').text(award.awardDesc);
                        $('#award-img').attr('src',award.awardImg);
                        var expireTime = new Date(award.expireTime).Format("yyyy-MM-dd");
                        $("#award-time").val(expireTime);
                        $("#award-point").text(award.point+'积分');
                        /*var productCategoryList = data.productCategoryList;
                        var html = '';
                        productCategoryList
                                .map(function(item, index) {
                                    html += '<a href="#" class="button" data-product-search-id='
                                            + item.productCategoryId
                                            + '>'
                                            + item.productCategoryName
                                            + '</a>';
                                });
                        $('#shopdetail-button-div').html(html);*/
                    }
                });
    }




    $('#me').click(function() {
        $.openPanel('#panel-left-demo');
    });

    $("#purchase").click(function () {
        $.confirm('确定购买吗?', function() {
            // alert(point+"--"+productId);
            $.ajax({
                url : '/frontend/purchaseproduct',
                type : 'POST',
                data : {
                    // point : point,
                    productId : productId
                },
                dataType : 'json',
                success : function(data) {
                    if (data.success) {
                        $.toast('购买成功！');
                    } else {
                        $.toast('购买失败！');
                    }
                }
            });
        });
    });
    $.init();


});
