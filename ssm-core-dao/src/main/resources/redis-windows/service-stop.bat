rem stop redis service
redis-server --service-stop --service-name redis6379
redis-server --service-stop --service-name redis6380
redis-server --service-stop --service-name redis6381

rem start sentinel service
redis-server --service-stop --service-name sentinel16379
redis-server --service-stop --service-name sentinel26379
redis-server --service-stop --service-name sentinel36379