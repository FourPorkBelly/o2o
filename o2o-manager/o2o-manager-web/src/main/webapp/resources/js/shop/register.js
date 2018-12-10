$(function() {
	//var registerUrl = '/myo2o/shop/ownerregister';
    var registerUrl = '/shop/ownerregister';
	$('#submit').click(function() {
		var userName = $('#userName').val();
		var password = $('#password').val();
		var phone = $('#phone').val();
		var email = $('#email').val();
		var name = $('#name').val();

		var formData = new FormData();
		formData.append('thumbnail', thumbnail);
		formData.append('localAuthStr', JSON.stringify(localAuth));
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			return;
		}
		formData.append("verifyCodeActual", verifyCodeActual);
		$.ajax({
			url : registerUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
					window.location.href = '/shop/ownerlogin';
				} else {
					$.toast('提交失败！');
					$('#captcha_img').click();
				}
			}
		});
	});

	$('#back').click(function() {
		window.location.href = '/shop/ownerlogin';
	});
	$('#small-img').change(function () {
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
                        $("#profile_img").show();
						$("#profile_img").attr("src",data.url);
                    }else {
                        alert(data.message)
                    }
                }
            }
        )
        return false;
    }
});
