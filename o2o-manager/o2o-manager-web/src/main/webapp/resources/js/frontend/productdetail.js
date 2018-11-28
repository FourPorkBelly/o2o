$(function() {
	var productId = getQueryString('productId');
	var point='';
	var productUrl = '/frontend/listproductdetailpageinfo?productId='
			+ productId;

	$
			.getJSON(
					productUrl,
					function(data) {
						if (data.success) {
							var product = data.product;
							$('#product-img').attr('src', product.imgAddr);
							$('#product-time').text(
									new Date(product.lastEditTime)
											.Format("yyyy-MM-dd"));
							$('#product-name').text(product.productName);
							$('#product-desc').text(product.productDesc);
							//获取商品积分
							point=product.point;
							//获取商品id
                            productId=product.productId;
							var imgListHtml = '';
							product.productImgList.map(function(item, index) {
								imgListHtml += '<div> <img src="'
										+ item.imgAddr + '"/></div>';
							});
							// 生成购买商品的二维码供商家扫描
							/*imgListHtml += '<div> <img src="/myo2o/frontend/generateqrcode4product?productId='
									+ product.productId + '"/></div>';
							$('#imgList').html(imgListHtml);*/
						}
					});
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
