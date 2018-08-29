angular.module('home_module', []).controller('homeController', homeController);

function homeController($scope) {
    $scope.dataList = [];

    /**
     * 从后台获取数据
     */
    $scope.loadData = function () {
        $.ajax({
            url: apiUrl + "/user/listAll",
            type: "get",
            dataType: "json",
            data: {
            },
            success: function(resp) {
                if (resp && resp.result == 200) {
                    $scope.dataList = resp.data.data;
                    $scope.$apply();
                } else {
                    layer.msg('获取用户列表失败');
                }
            },
            error: function() {
                layer.msg('获取用户列表失败');
            }
        });
    };

    $scope.loadData();

    /**
     * 注销登录
     */
    $scope.logout = function () {
        $.ajax({
            url: apiUrl + "/logout",
            type: "post",
            dataType: "json",
            data: {
            },
            success: function(resp) {
                if (resp && resp.result == 200) {
                    window.location.reload();
                }
            },
            error: function() {
                layer.msg('Failed to log out');
            }
        });
    }
}
