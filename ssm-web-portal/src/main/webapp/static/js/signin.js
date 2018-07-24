/**
 * 多个tips可以同时显示
 */
layer.config({
    tipsMore:true
});

/**
 * 记住我
 */
$(".zt").hide(); //默认不选中
$("#remember-me").click(function(){
    var n = document.getElementById("remember-me").checked;
    if(n){
        $(".zt").show();
    }else{
        $(".zt").hide();
    }
});

/**
 * 验证码
 */
$('.imgcode').hover(function () {
    layer.tips("看不清？点击更换", '.verify', {
        time: 6000,
        tips: [2, "#3c3c3c"]
    })
}, function () {
    layer.closeAll('tips');
}).click(function () {
    $(this).attr('src', 'http://zrong.me/home/index/imgcode?id=' + Math.random());
});

/**
 * Validate Username
 * @type {Array}
 */
var usernameIndex = [];
function validateUsername() {
    var username = $.trim($("#username").val());
    if(username == null || username == ""){
        usernameIndex.push(layer.tips("请输入账号", '#username', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        $("#username").focus();
        return false;
    } else {
        $.each(usernameIndex, function (index, value) {
            layer.close(value);
        });
        return true;
    }
}
$("#username").on('input propertychange',validateUsername);

/**
 * Validate Password
 */
var passwordIndex = [];
function validatePassword() {
    var password = $.trim($("#password").val());
    if (password == null || password == "") {
        passwordIndex.push(layer.tips("密码不能为空", '#password', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        $("#password").focus();
        return false;
    } else {
        $.each(passwordIndex, function (index, value) {
            layer.close(value);
        });
        return true;
    }
}
$("#password").on('input propertychange',validatePassword);

/**
 * 登录
 * @returns {boolean}
 */
function doSignIn() {
    var isUsername = validateUsername();
    var isPassword = validatePassword();
    if(!(isUsername && isPassword)){
        return false;
    }

 /*   password = md5(password + "PFZN-i9<RFXJ%Itzn,Q$M;tFFCaD;*[C]jmRY.hOW,YA*_B@mq<.3kj+I,&jrfdd");
    console.log("new:" + password);*/

    $.ajax({
        url: apiUrl + "/doSignIn",
        type: "post",
        dataType: "json",
        data: {
            username:$.trim($("#username").val()),
            password:$.trim($("#password").val()),
            rememberme:document.getElementById("remember-me").checked
        },
        success: function(resp) {
            if (resp && resp.result == 200) {
                window.location.href = contextPath + "/index";
            } else {
                var msg='系统故障';
                var location = '#username';
                if(resp.message == 'ACCOUNT'){
                    msg='用户名不存在';
                    location = '#username';
                }
                if(resp.message == 'PASSWORD'){
                    msg='密码错误';
                    location = '#password';
                }
                if(resp.message == 'LOCKED'){
                    msg='账户已被锁定，请联系管理员';
                    location = '#username';
                }
                if(resp.message == 'AUTH'){
                    msg='您没有权限';
                    location = '#username';
                }

                layer.tips(msg, location, {
                    tips: [2, '#cc3a1e'],
                    time: 2000
                });
            }
        },
        error: function() {
            layer.msg('接口调用失败');
        }
    });

}