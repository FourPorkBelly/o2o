$(function() {
	var awardId = getQueryString('awardId');
	var shopId = getQueryString('shopId');
	var infoUrl = '/shop/getawardbyid?awardId=' + awardId;
	var awardPostUrl = '/shop/modifyaward';
	var isEdit = false;
	if (awardId) {
		getInfo(awardId);
		isEdit = true;
	} else {
		awardPostUrl = '/shop/addaward?shopId='+shopId;
	}
    var jq=$.noConflict();
	function getInfo(id) {
		$.getJSON(infoUrl, function(data) {
			if (data.success) {
				var award = data.award;
				$('#award-name').val(award.awardName);
				$('#priority').val(award.priority);
				$('#award-desc').val(award.awardDesc);
				$('#point').val(award.point);
				$('#awardI').attr('src',award.awardImg);
                $('#awardI').show();
				$('#awardImg').val(award.awardImg);
				var expireTime = new Date(award.expireTime).Format("yyyy-MM-dd");
				$("#pass-date").val(expireTime);
                $("#pass-date").calendar({
                    value: [expireTime]
                });
			}else{
				alert(data.errMsg);
                history.back(-1);
			}
		});
	}

	$('#submit').click(function() {
		var awardName = $('#award-name').val();
        if (!awardName) {
            $.toast('请输入奖品名称！');
            $("#award-name").focus();
            return;
        }
		var priority = $('#priority').val();
        if (!priority) {
            $.toast('请输入优先级！');
            $("#priority").focus();
            return;
        }
        var point = $('#point').val();
        if (!point) {
            $.toast('请输入需要积分！');
            $("#point").focus();
            return;
        }
		var awardDesc = $('#award-desc').val();
        if (!awardDesc) {
            $.toast('请输入时间！');
            $("#award-desc").focus();
            return;
        }
		var awardImg = $("#awardImg").val();
        if (!awardImg) {
            $.toast('请上传图片！');
            return;
        }
		var expireTime = $('#pass-date').val();
        if (!expireTime) {
            $.toast('请选择过期时间！');
            return;
        }
        if(compare_time(expireTime,getNowFormatDate())){
            $.toast('过期日期要大于当前日期！');
            return;
		}
		//alert(expireTime)
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast('请输入验证码！');
			$("#j_captcha").focus();
			return;
		}
		jq.post(awardPostUrl, $("#awardForm").serialize(),
            function(data){
                if (data.success) {
                    alert(data.errMsg);
                    window.location.href="/shop/awardmanage?shopId="+shopId;
                } else {
                    $.toast(data.errMsg);
                    $('#captcha_img').click();
                }
            }, "json");
	});
/*//获取当前时间，格式YYYY-MM-DD*/
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }

	/* 日期比较 */
    function compare_time(a,b) {
        var arr=a.split("-");
        var starttime=new Date(arr[0],arr[1],arr[2]);
        var starttimes=starttime.getTime();
        var arrs=b.split("-");
        var endtime=new Date(arrs[0],arrs[1],arrs[2]);
        var endtimes=endtime.getTime();
        if(starttimes>endtimes)//开始大于结束
        {
            return false;
        }
        else{
            return true;
        }
    }
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
        jq.ajaxFileUpload
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
                            $("#awardI").show();
                            $("#awardI").attr("src",data.url);
                            $("#awardImg").val(data.url);
                    }else {
                        alert(data.message)
                    }
                }
            }
        )
        return false;
    }
    $("#pass-date").calendar({
        value: [getNowFormatDate()]
    });
});