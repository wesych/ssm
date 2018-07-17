rem uninstall redis service
redis-server --service-uninstall --service-name redis6379
redis-server --service-uninstall --service-name redis6380
redis-server --service-uninstall --service-name redis6381

rem uninstall sentinel service
redis-server --service-uninstall --service-name sentinel16379
redis-server --service-uninstall --service-name sentinel26379
redis-server --service-uninstall --service-name sentinel36379