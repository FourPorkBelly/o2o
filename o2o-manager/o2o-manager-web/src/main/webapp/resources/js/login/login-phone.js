$(function () {
    var phoneurl = "";
    $(".phone-button-a").click(function () {
        var phone = $("#phone").val();
        var reg = /^1[3|4|5|7|8][0-9]{9}$/; //验证规则
        if(!reg.test(phone)){
            alert("请填写正确的手机号码");
            return;
        }
        $.post(phoneurl, { "phone": phone },
            function(data){
                alert(data); // John
            }, "json");
    })
})