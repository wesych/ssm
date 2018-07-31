<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>几遇 - 遇见是一种美好</title>
    <link rel="shortcut icon" type="image/x-icon" href="${contextPath}/static/images/ameet.ico" />
    <link rel="stylesheet" type="text/css" href="${contextPath}/static/bootstrap/css/bootstrap.css">
</head>
<body>
<div id="vueApp">
    Welcome to Vue.js!<br/>

    <table class="table">
        <caption>Vue数据遍历</caption>
        <caption>{{msg}}</caption>
        <caption>{{userList.length}}</caption>
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
        <tr v-for="item in userList">
            <td>{{msg}}</td>
            <td>{{item.nickname}}</td>
            <td>{{item.mobile}}</td>
            <td>{{item.email}}</td>
            <td>{{item.gender == 1 ? '男' : '女'}}</td>
            <td>{{item.city}}</td>
            <td>{{item.birth}}</td>
        </tr>
        </tbody>
    </table>
</div>

<script>
    var contextPath='${contextPath}';
    var apiUrl='${apiUrl}';
</script>
<script src='${contextPath}/static/js/jquery.min.js' type="text/javascript"></script>
<script src='${contextPath}/static/bootstrap/js/bootstrap.js' type="text/javascript"></script>
<script src='${contextPath}/static/layer/layer.js' type="text/javascript"></script>

<script src='${contextPath}/static/js/vue/vue.js' type="text/javascript"></script>
<script src='${contextPath}/static/js/vue/axios.js' type="text/javascript"></script>
<script src='${contextPath}/static/js/vue/user.js' type="text/javascript"></script>

</body>
</html>