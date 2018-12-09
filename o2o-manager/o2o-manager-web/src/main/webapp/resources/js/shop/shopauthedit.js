$(function() {
	var shopAuthId = getQueryString('shopauthId');
    var shopId = getQueryString('shopId');
	var infoUrl = '/shop/getshopauthmapbyid?shopAuthId=' + shopAuthId;

	var shopAuthPostUrl = '/shop/modifyshopauthmap';
	if (shopAuthId) {
		getInfo(shopAuthId);
	} else {
		alert('用户不存在！');
		window.location.href = '/shop/shopmanage';
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
		var shopAuth = {};
		shopAuth.name = $('#shopauth-name').val();
		shopAuth.title = $('#title').val();
		shopAuth.shopAuthId = shopAuthId;
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
				shopAuthMapStr : JSON.stringify(shopAuth),
				verifyCodeActual : verifyCodeActual
			},
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					$('#captcha_img').click();
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

});