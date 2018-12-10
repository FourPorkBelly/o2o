$(function() {
	var shopAuthId = getQueryString('shopauthId');
    var shopId = getQueryString('shopId');
	var infoUrl = '/shop/getshopauthmapbyid?shopAuthId=' + shopAuthId;

	var shopAuthPostUrl = '/shop/modifyshopauthmap';
	if (shopAuthId) {
		getInfo(shopAuthId);
	} else {
		alert('用户不存在！');
		window.location.href = '/shop/shopmanage?shopId='+shopId;
	}
    $("a").each(function (i,e) {
        var url = $(e).attr("href");
        $(e).attr("href",url+"?shopId="+shopId);
    })
	function getInfo(id) {
		$.getJSON(infoUrl, function(data) {
			if (data.success) {
				var shopAuthMap = data.shopAuthMap;
				$('#shopauth-name').val(shopAuthMap.name);
                $('#shopauth-name').attr("disabled","disabled");
				$('#title').val(shopAuthMap.title);
			}
		});
	}

	$('#submit').click(function() {
		var title = $('#title').val();
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		$.ajax({
			url : shopAuthPostUrl,
			type : 'POST',
			contentType : "application/x-www-form-urlencoded; charset=utf-8",
			data : {
                title : title,
                j_captcha:verifyCodeActual

			},
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					$('#captcha_img').click();
					window.location.href="/shop/shopmanage?shopId='+shopId";
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

});