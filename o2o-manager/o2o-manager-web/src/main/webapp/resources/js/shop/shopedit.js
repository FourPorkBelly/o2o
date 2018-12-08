$(function() {
    $.config = {router: false}
	var shopId = getQueryString("shopId");
	var isEdit = shopId ? true : false;
    $("#shopId").val("");
	var shopInfoUrl = '/shop/getshopbyid?shopId='+shopId;
	// var shopInfoUrl = '/myo2o/shop/getshopbyid?shopId=' + shopId;
	var initUrl = '/shop/getshopinitinfo';
	var editShopUrl = '/shop/registershop';
	var shoplist = "shop/shoplist";
	if (isEdit) {
		editShopUrl = '/shop/modifyshop';
	}

	function getInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
                $("#shopId").val(shop.shopId);
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
                $("#hximage").show();
                $("#hximage").attr("src",shop.shopImg);
                $("#shopImg").val(shop.shopImg);
				var shopCategory = '<option value="'+shop.shopCategory.shopCategoryId+'" data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
				    if(item.areaId==shop.areaId){
                        tempAreaHtml += '<option value="'+item.areaId+'" data-id="' + item.areaId + '" selected>'
                            + item.areaName + '</option>';
                    }else{
                        tempAreaHtml += '<option value="'+item.areaId+'" data-id="' + item.areaId + '">'
                            + item.areaName + '</option>';
                    }

				});
				$('#shop-parent').html(shopCategory);
				$('#shop-parent').attr('disabled','disabled');
				$('#area').html(tempAreaHtml);
				$('#area').attr('data-id',shop.areaId);
                getCategoryId(shop.shopCategory.shopCategoryId);
                $('#shop-category').attr('disabled','disabled');
			}
		});
	}

	function getCategory() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option value="'+item.shopCategoryId+'" data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option value="'+item.areaId+'" data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-parent').html(tempHtml);
				$('#shop-parent').removeAttr('disabled');
				$('#area').html(tempAreaHtml);
                getCategoryId(data.shopCategoryList[0].shopCategoryId);
			}
		});
	}
    $("#shop-parent").change(function () {
        getCategoryId($(this).val());
    })
    function getCategoryId(parentId){
        $.getJSON(initUrl+"?parentId="+parentId, function(data) {
            if (data.success) {
                var tempHtml = '';
                data.shopCategoryList.map(function(item, index) {
                    tempHtml += '<option value="'+item.shopCategoryId+'" data-id="' + item.shopCategoryId
                        + '">' + item.shopCategoryName + '</option>';
                });
                $('#shop-category').html(tempHtml);
            }
        });
    }

	if (isEdit) {
		getInfo(shopId);
	} else {
		getCategory();
	}

    $('#submit').click(function(){
        /* 表单校验 */
        var shopname = $("#shop-name").val();
        if(shopname==null||shopname==""){
            $("#shop-name").focus();
            alert("商铺名称不能为空");
            return;
        }
        var shopaddr = $("#shop-addr").val();
        if(shopaddr==null||shopaddr==""){
            $("#shop-addr").focus();
            alert("详细地址不能为空");
            return;
        }

        var shopphone = $("#shop-phone").val();
        if(shopphone==null||shopphone==""){
            $("#shop-phone").focus();
            alert("详手机号码不能为空");
            return;
        }
        var reg = /^1[3|4|5|7|8][0-9]{9}$/; //验证规则
        if(!reg.test(shopphone)){
            alert("请填写正确的手机号码");
            return;
        }

        var shopImg = $("#shopImg").val();
        if(shopImg==null||shopImg==""){
            alert("请上传图片");
            return;
        }
        var shopdesc = $("#shop-desc").val();
        if(shopdesc==null||shopdesc==""){
            $("#shop-desc").focus();
            alert("店铺简介不能为空");
            return;

        }
        /* 表单提交ajax */
        jQuery.post(editShopUrl, $("#shopfrom").serialize(),
            function(data){
                alert(data.errMsg);
                if(data.success){
                    window.location.href="/shop/shoplist.html";
                }
            }, "json");
        //$("#shopfrom").submit();
	})
	/* 图片验证上传 */
	$("#imgAddr").change(function () {
		var img = $(this).val();
        if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(img)) {
            alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
            $(this).val("");
        }
        var ajaxUrl = "update/upload";
        $.ajaxFileUpload
        (
            {
                url:ajaxUrl, //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'imgAddr', //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data)  //服务器成功响应处理函数
                {
                   if(data.error==0){
                       $("#hximage").show();
                       $("#hximage").attr("src",data.url);
                       $("#shopImg").val(data.url);
				   }else {
                   		alert(data.message)
				   }
                }
            }
        )
        return false;
    })
});