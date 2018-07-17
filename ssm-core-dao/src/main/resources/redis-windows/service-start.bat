rem start redis service
redis-server --service-start --service-name redis6379
redis-server --service-start --service-name redis6380
redis-server --service-start --service-name redis6381

rem start sentinel service
redis-server --service-start --service-name sentinel16379
redis-server --service-start --service-name sentinel26379
redis-server --service-start --service-name sentinel36379