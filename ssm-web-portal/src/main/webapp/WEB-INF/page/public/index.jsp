<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>几遇 - 遇见是一种美好</title>
    <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/static/images/ameet.ico" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/bootstrap/css/bootstrap.css">
</head>
<body ng-app="ameet_home_module" ng-controller="homeController" ng-cloak class="ng-cloak">
    Welcome to JIYU!<br/>

    <shiro:guest>
        您当前是游客，<a href="signin">登录</a><a href="signup">注册</a><br/>
    </shiro:guest>
    <shiro:authenticated>
        用户[<shiro:principal property="mobile"/>]已通过身份验证<br/>
    </shiro:authenticated>
    <shiro:notAuthenticated>
        当前身份未认证（包括记住我登录的）<br/>
    </shiro:notAuthenticated>
    <shiro:user>
        欢迎[<shiro:principal property="account"/>]，<a ng-click="logout()">退出</a><br/>
        <table class="table">
            <caption>基本的表格布局</caption>
            <thead>
            <tr>
                <th>账号</th>
                <th>昵称</th>
                <th>手机</th>
                <th>邮箱</th>
                <th>性别</th>
                <th>城市</th>
                <th>出生日期</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="row in userList">
                <td>{{row.account}}</td>
                <td>{{row.nickname}}</td>
                <td>{{row.mobile}}</td>
                <td>{{row.email}}</td>
                <td>{{row.gender == 1 ? '男' : '女'}}</td>
                <td>{{row.city}}</td>
                <td>{{row.birth | date:'yyyy-MM-dd'}}</td>
            </tr>
            </tbody>
        </table>
    </shiro:user>


    <script src='${contextPath}/static/js/jquery.min.js' type="text/javascript"></script>
    <script src='${contextPath}/static/js/angular.min.js' type="text/javascript"></script>
    <script src='${contextPath}/static/bootstrap/js/bootstrap.min.js' type="text/javascript"></script>
    <script src='${contextPath}/static/layer/layer.js' type="text/javascript"></script>
    <script src='${contextPath}/static/js/home.js' type="text/javascript"></script>
    <script>
        var contextPath='${contextPath}';
    </script>
</body>
</html>