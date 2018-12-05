$(function() {
	//从url里获取productId的值
	var productId = getQueryString('productId');
	//测试的商铺id**********************************
	var shopId = 20;
	//通过productId获取商品信息的url
	var infoUrl = '/product/getproductbyid?productId=' + productId;
	//获取当前店铺商品类别的url
	var categoryUrl = '/product/getproductcategorylistbyshopId?shopId='
			+ shopId;
	//更新商品的url
	var productPostUrl = '/product/updateProduct';
	//由于商品添加与修改是同一个页面
	//该标识符用来标明本次是添加还是修改
	var isEdit = false;
	if (productId) {
		getInfo(productId);
		isEdit = true;
	} else {
		getCategory(shopId);
		productPostUrl = '/product/modifyproduct';
	}
	//获取需要编辑的商品的信息，并赋值给表单
	function getInfo(id) {
		$.getJSON(
						infoUrl,
						function(data) {
							if (data.success) {
								//从返回的json中获取商品对象的信息，并赋值给表单

								var product = data.product;
								$('#imgAddr').val(product.imgAddr);
                                // $('#imgAddrs').val(data.imgAddrs);
								$('#product-name').val(product.productName);
								$('#product-desc').val(product.productDesc);
								$('#priority').val(product.priority);
								$('#normal-price').val(product.normalPrice);
								$('#promotion-price').val(product.promotionPrice);

								//显示商品图片
								//缩略图
								$("#imgAddr").attr("src",product.imgAddr);
								$("#imgAddr").show();
								$("#imgsrc0").attr("src",product.productImgs[0].imgAddr);
                                $("#imgsrc0").show();
								//详情图片
								for (var i=1;i<product.productImgs.length;i++){
                                    $('#detail-img').append('<img id="imgsrc'+imgindex+'" src='+product.productImgs[i].imgAddr+' width="60px" height="60px"><br><input type="file" class="detail-img" name="uploadFile" id="updateimg'+(imgindex)+'" indexx='+imgindex+' >');
                                    imgindex++;
								}
								//获取原本的商品类别，店铺所有商品类别
								var optionHtml = '';
								var optionArr = data.productCategoryList;
								var optionSelected = product.productCategoryId;
								// alert(optionSelected);
								// 生成商品类别list，并默认选择编辑前的商品类别
								optionArr
										.map(function(item, index) {

											var isSelect = optionSelected === item.productCategoryId ? 'selected'
													: '';
											optionHtml += '<option data-value="'
													+ item.productCategoryId
													+ '"'
													+ isSelect
													+ '>'
													+ item.productCategoryName
													+ '</option>';
										});
								$('#category').html(optionHtml);
							}
						});
	}

	function getCategory() {
		$.getJSON(categoryUrl, function(data) {
			if (data.success) {
				var productCategoryList = data.productCategoryList;
				var optionHtml = '';
				productCategoryList.map(function(item, index) {
					optionHtml += '<option value="'
							+ item.productCategoryId + '">'
							+ item.productCategoryName + '</option>';
				});
				$('#category').html(optionHtml);
			}
		});
	}
	//针对商品详情图控件组，若最后一个控件发生变化，则生成新的控件（不超过6个）
	var imgindex=1;
	$('.detail-img-div').on('change','.detail-img:last-child', function() {
        updateimgss(this);
		if ($('.detail-img').length < 6) {
			$('#detail-img').append('<img id="imgsrc'+imgindex+'" src="" width="60px" height="60px" hidden><input type="file" class="detail-img" name="uploadFile" id="updateimg'+(imgindex)+'" indexx='+imgindex+' >');
		}
        imgindex++;

	});
	/*$("#updateimg0").change(function () {
		alert($(this).val())
    })*/

    $('#submit').click(
        function() {
            /* 表单校验 */
            var productname = $("#product-name").val();
            if(productname==null||productname==""){
                $("#product-name").focus();
                alert("商品名称不能为空");
                return;
            }
            var productdesc = $("#product-desc").val();
            if(productdesc==null||productdesc==""){
                $("#product-desc").focus();
                alert("商品描述不能为空");
                return;
            }
            // var productsmall = $("#small-img").val();
            // if(productsmall==null||productsmall==""){
            //     $("#small-img").focus();
            //     alert("缩略图不能为空");
            //     return;
            // }
            var priority = $("#priority").val();
            if(priority==null||priority==""){
                $("#priority").focus();
                alert("优先级不能为空");
                return;
            }
            var normalprice = $("#normal-price").val();
            if(normalprice==null||normalprice==""){
                $("#normal-price").focus();
                alert("原价不能为空");
                return;
            }
            // var updateimg0 = $("#updateimg0").val();
            // if(updateimg0==null||updateimg0==""){
            //     $("#normal-price").focus();
            //     alert("详细图不能为空");
            //     return;
            // }
            /* 表单提交ajax */
            // var ssss = $("#productForm").serialize();
			alert(productPostUrl);
            jQuery.post(productPostUrl, $("#productForm").serialize(),
                function(data){
                    // $('#productId').val(data.productId);

                    alert(data.errMsg);
                    window.location.href="productmanage.html"
                }, "json");
		})
	$("#small-img").change(function () {
        updateimgss(this);
    })
    /* 图片验证上传 */
    function updateimgss(img) {
    	var imgval = $(img).val();
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(imgval)) {
            alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
            $(img).val("");
            return;
        }
        var imgid = $(img).attr("id");
        var ajaxUrl = "update/upload";
        $.ajaxFileUpload
        (
            {
                url:ajaxUrl, //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: imgid, //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                	//判断是否上传成功
                    if(data.error==0){
                        if(imgid=="small-img"){
                        	$("#imgAddr").show();
                            $("#imgAddr").attr("src",data.url);
						}else{
                            var url = $('#shopImgs').val();
                            if(url!=""&&url!=null){
                                $('#shopImgs').val(url+","+data.url)
                            }else{
                                $('#shopImgs').val(url+data.url)
                            }
                            $("#imgsrc"+$(img).attr("indexx")).attr("src",data.url);
                            $("#imgsrc"+$(img).attr("indexx")).show();
						}

                    }else {
                        alert(data.message)
                    }
                }
            }
        )
        return false;
    }
});