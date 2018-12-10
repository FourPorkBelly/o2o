$(function() {
	var productId = getQueryString('productId');
	var point='';
	var isproductgo = getQueryString('isproductgo');
	var productUrl = '/frontend/listproductdetailpageinfo?productId='
			+ productId;

	$
			.getJSON(
					productUrl,
					function(data) {
                        if(isproductgo!=null||isproductgo!=""){
                            $(".div-hide").hide();
                        }
						if (data.success) {
							var product = data.product;
							/*$('#product-img').attr('src', product.imgAddr);*/
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
							product.productImgs.map(function(item, index) {
								/*imgListHtml += '<div style="height: 100%;width: 100%"> <img src="'
										+ item.imgAddr + '"/></div>';*/
								imgListHtml += '<div align="center" valign="bottom" class="card-header color-white no-border no-padding">' +
									'<img style="margin: 0px auto" src="'+item.imgAddr+'" alt="">' +
                                    '</div>';
							});
                            $('#imgList>div').html(imgListHtml);
							// 生成购买商品的二维码供商家扫描
							imgListHtml += '<div> <img src="/frontend/generateqrcode4product?productId='
									+ product.productId + '"/></div>';
							//$('#imgList').html(imgListHtml);
                            ins.reload({
                                elem: '#imgList'
								,width:'100%'
                                , arrow: 'none'
                                //,arrow: 'always' //始终显示箭头
                                //,anim: 'updown' //切换动画方式
							})
						}
					});
	$('#me').click(function() {
		$.openPanel('#panel-left-demo');
	});
	/* 左右滑动切换轮播图 */
    $("#imgList").on("touchstart", function (e) {
        var startX = e.originalEvent.targetTouches[0].pageX;//开始坐标X
        $(this).on('touchmove', function (e) {
            arguments[0].preventDefault();//阻止手机浏览器默认事件
        });
        $(this).on('touchend', function (e) {
            var endX = e.originalEvent.changedTouches[0].pageX;//结束坐标X
            e.stopPropagation();//停止DOM事件逐层往上传播
            if (endX - startX > 30) {
                ins.slide("sub");
            }
            else if (startX - endX > 30) {
                ins.slide("add");
            }
            $(this).off('touchmove touchend');
        });
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
	/*$.init();*/


});
