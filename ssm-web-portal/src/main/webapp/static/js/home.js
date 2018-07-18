angular.module('ameet_home_module', [])
    .controller('homeController', homeController);

function homeController($scope, $http) {

    /**
     * 从后台获取数据
     */
    $scope.loadData = function () {
        $http.get(contextPath + "/getUser", {
            params: {
                "var1": "AAAA",
                "var2": "BBBB"
            }
        }).success(function (data) {
            $scope.userList = data;
        });
    };

    $scope.loadData();

    /**
     * 注销登录
     */
    $scope.logout = function () {
        $.ajax({
            url: contextPath + "/logout",
            type: "post",
            dataType: "json",
            data: {
            },
            success: function(resp) {
                if (resp && resp.result == 1) {
                    window.location.reload();
                }
            },
            error: function() {
                layer.msg('Failed to log out');
            }
        });
    }
}
