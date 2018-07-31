let vm = new Vue({
    el: '#vueApp',
    data: {
        userList : [],
        msg: 'big sha bi'
    },
    created: function () {
        this.loadUserList()
    },
    methods: {
        loadUserList: function(){
            let that = this; // 在axios中使用this会导致指向性问题
            axios.get(apiUrl + "/user/listAll")
                .then(function (resp) {
                    console.log(resp.data.data.data);
                    that.userList = resp.data.data.data;
                    console.log('Success');
                })
                .catch(function (error) {
                    console.log(error);
                    layer.msg('获取用户列表失败');
                });
        }
    },
});