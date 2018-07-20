<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>几遇 - 遇见是一种美好</title>
    <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/static/images/ameet.ico" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/style/register-login.css">
</head>
<body>
<div id="box"></div>
<div class="cent-box">
    <div class="cent-box-header">
        <h1 class="main-title hide">几遇</h1>
        <h2 class="sub-title">遇见，是一种美好</h2>
    </div>

    <div class="cont-main clearfix">
        <div class="index-tab">
            <div class="index-slide-nav">
                <a href="signin" class="active">登录</a>
                <a href="signup">注册</a>
                <div class="slide-bar"></div>
            </div>
        </div>

        <div class="login form">
            <div class="group">
                <div class="group-ipt username">
                    <input type="text" name="username" id="username" class="ipt" placeholder="手机/邮箱" required autocomplete="off">
                </div>
                <div class="group-ipt password">
                    <input type="password" name="password" id="password" class="ipt" placeholder="输入您的登录密码" required autocomplete="off">
                </div>
                <%--<div class="group-ipt verify">
                    <input type="text" name="verify" id="verify" class="ipt" placeholder="输入验证码" required>
                    <img src="" class="imgcode">
                </div>--%>
            </div>
        </div>

        <div class="button">
            <button type="submit" class="login-btn register-btn" id="button" onclick="javascript:doSignIn()">登录</button>
        </div>

        <div class="remember clearfix">
            <label class="remember-me"><span class="icon"><span class="zt"></span></span>
                <input type="checkbox" name="remember-me" id="remember-me" class="remember-mecheck">记住我</label>
            <label class="forgot-password">
                <a href="#">忘记密码？</a>
            </label>
        </div>
    </div>
</div>

<div class="footer">
    <p>几遇随笔 · 几遇咖啡 · 几遇电影 · 几遇音乐 · 几遇旅游</p>
</div>

<script src='${contextPath}/static/js/particles.js' type="text/javascript"></script>
<script src='${contextPath}/static/js/background.js' type="text/javascript"></script>
<script src='${contextPath}/static/js/jquery.min.js' type="text/javascript"></script>
<script src='${contextPath}/static/layer/layer.js' type="text/javascript"></script>
<script src='${contextPath}/static/js/signin.js' type="text/javascript"></script>
<script src='${contextPath}/static/js/md5.min.js' type="text/javascript"></script>
<script>
    var contextPath='${contextPath}';
</script>
</body>
</html>