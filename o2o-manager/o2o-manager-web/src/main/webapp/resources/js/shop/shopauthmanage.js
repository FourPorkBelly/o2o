$(function() {
    var shopId = getQueryString('shopId');
    var listUrl = '/shop/listshopauthmapsbyshop?pageIndex=1&pageSize=9999&shopId=' + shopId;
    var deleteUrl = '/shop/removeshopauthmap';
    $("a").each(function (i,e) {
        var url = $(e).attr("href");
        $(e).attr("href",url+"?shopId="+shopId);
    })
    function getList() {
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var shopauthList = data.shopAuthMapList;
                var tempHtml = '';
                shopauthList.map(function (item, index) {
                    tempHtml += ''
                         +      '<div class="row row-shopauth">'
                         +          '<div class="col-40">'+ item.employee.name +'</div>'
                         +          '<div class="col-20">'+ item.title +'</div>'
                         +          '<div class="col-40">'
                         +              '<a href="#" class="edit" data-employee-id="'+ item.employeeId +'" data-auth-id="'+ item.shopAuthId +'" data-status="'+ item.enableStatus +'">编辑</a>'
                         +              '<a href="#" class="delete" data-employee-id="'+ item.employeeId +'" data-auth-id="'+ item.shopAuthId +'" data-status="'+ item.enableStatus +'">删除</a>'
                         +          '</div>'
                         +      '</div>';
                });
                $('.shopauth-wrap').html(tempHtml);
                // 生成购买商品的二维码供商家扫描
                var imgHtml = '<div> <img src="/shop/generateqrcode4product?productId='
                    + product.productId + '"/></div>';
                $('#erweima').html(imgHtml);
            }
        });
    }

    getList();

    function deleteItem(id) {
        $.confirm('确定么?', function () {
            $.ajax({
                url: deleteUrl,
                type: 'POST',
                data: {
                    shopAuthId: id,
                },
                dataType: 'json',
                success: function (data) {
                    if (data.success) {
                        $.toast('操作成功！');
                        getList();
                    } else {
                        $.toast('操作失败！');
                    }
                }
            });
        });
    }

    $('.shopauth-wrap').on('click', 'a', function (e) {
        var target = $(e.currentTarget);
        if (target.hasClass('edit')) {
            window.location.href = '/shop/shopauthedit?shopauthId=' + e.currentTarget.dataset.authId+"&shopId="+shopId;
        } else if (target.hasClass('delete')) {
            deleteItem(e.currentTarget.dataset.authId);
        }
    });

    // $('#new').click(function () {
    //     window.location.href = '/myo2o/shop/shopauthedit';
    // });
});