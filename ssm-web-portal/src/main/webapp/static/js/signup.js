/**
 * 多个tips可以同时显示
 */
layer.config({
    tipsMore:true
});

/**
 * 唯一性检测(data: account, mobile, email)
 */
var accountExist;
var mobileExist;
var emailExist;
function checkUniqueness(data, field) {
    $.ajax({
        async: false,
        url : apiUrl + "/checkUniqueness",
        type : "POST",
        data : data,
        dataType : 'json',
        success : function(resp) {
            if(resp.result != 200){
                if(field == 'ACCOUNT'){
                    accountExist = true;
                }
                if(field == 'MOBILE'){
                    mobileExist = true;
                }
                if(field == 'EMAIL'){
                    emailExist = true;
                }
            } else {
                accountExist = false;
                mobileExist = false;
                emailExist = false;
            }
        }
    });
}

/**
 * 昵称校验
 */
var nameIndex = [];
function validateNickname() {
    var nickname = $.trim($("#nickname").val());
    if(nickname == null || nickname == ""){
        nameIndex.push(layer.tips("昵称不能为空", '#nickname', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        $("#nickname").focus();
        return false;
    } else {
        $.each(nameIndex, function (index, value) {
            layer.close(value);
        });
        return true;
    }
}
$("#nickname").bind("blur", validateNickname);
$("#nickname").on('input propertychange',validateNickname);

/**
 * 手机号校验
 */
var mobileIndex = [];
function validateMobile() {
    var mobile = $.trim($("#mobile").val());

    // 检验是否为空
    if(mobile == null || mobile == ""){
        mobileIndex.push(layer.tips("手机号不能为空", '#mobile', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        $("#mobile").focus();
        return false;
    } else {
        $.each(mobileIndex, function (index, value) {
            layer.close(value);
        });
    }

    // 检验是否含有非数字字符
    if(isNaN(mobile)){
        mobileIndex.push(layer.tips("手机号由11位数字组成", '#mobile', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        $("#mobile").focus();
        return false;
    } else {
        $.each(mobileIndex, function (index, value) {
            layer.close(value);
        });
    }

    // 检验长度
    if(mobile.length != 11) {
        mobileIndex.push(layer.tips("手机号长度必须为11位", '#mobile', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        $("#mobile").focus();
        return false;
    } else {
        // 检验有效性
        var reg = /^1[3|4|5|7|8][0-9]{9}$/;
        if(!reg.test(mobile)){
            mobileIndex.push(layer.tips("请输入正确的手机号", '#mobile', {
                tips: [2, '#3595CC'],
                time: 0
            }));
            $("#mobile").focus();
            return false;
        } else {
            $.each(mobileIndex, function (index, value) {
                layer.close(value);
            });
        }
    }

    //手机号唯一性
    var data = {mobile:mobile};
    checkUniqueness(data, 'MOBILE');
    if(mobileExist == true){
        mobileIndex.push(layer.tips("手机号已存在", '#mobile', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        $("#mobile").focus();
        return false;
    } else {
        $.each(mobileIndex, function (index, value) {
            layer.close(value);
        });
    }

    return true;
}
$("#mobile").bind("blur", validateMobile);



/**
 * 校验密码一
 */
var pwdIndex1 = [];
var pwdIndex2 = [];
function validatePassword1() {
    var password1 = $.trim($("#password1").val());
    if(password1 == null || password1.length == 0){
        pwdIndex1.push(layer.tips("密码不能为空", '#password1', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        $("#password1").focus();
        return false;
    } else {
        $.each(pwdIndex1, function (index, value) {
            layer.close(value);
        });
        return true;
    }
}
$("#password1").bind("blur", validatePassword1);

/**
 * 校验密码二
 * @returns {boolean}
 */
function validatePassword2() {
    var password1 = $.trim($("#password1").val());
    var password2 = $.trim($("#password2").val());
    validatePassword1();
    if(password1 != password2){
        pwdIndex2.push(layer.tips("密码不一致，请重新输入", '#password2', {
            tips: [2, '#3595CC'],
            time: 0
        }));
        return false;
    } else {
        $.each(pwdIndex2, function (index, value) {
            layer.close(value);
        });
        return true;
    }
}
$("#password2").bind("blur", validatePassword2);

/**
 * 注册
 */
function doSignUp() {
    var isNickname = validateNickname();
    var isMobile = validateMobile();
    var isPwd1 = validatePassword1();
    var isPwd2 = validatePassword2();
    if(!(isNickname && isMobile && isPwd1 && isPwd2)){
        return false;
    }

    // Sign Up
    $.ajax({
        url: apiUrl + "/doSignUp",
        type: "post",
        dataType: "json",
        data: {
            nickname:$.trim($("#nickname").val()),
            mobile:$.trim($("#mobile").val()),
            password:$.trim($("#password1").val())
        },
        success: function(resp) {
            if (resp && resp.result == 1) {
                layer.tips("注册成功", '#nickname', {
                    tips: [2, '#51cc1f'],
                    time: 2000
                });
                setTimeout(function () {
                    window.location.href = contextPath + "/signin";
                }, 2000);
            } else {
                layer.tips("注册失败", '#nickname', {
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