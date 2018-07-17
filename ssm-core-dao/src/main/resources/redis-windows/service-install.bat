rem install redis service
redis-server --service-install redis.master.6379.conf --service-name redis6379 --port 6379
redis-server --service-install redis.slave.6380.conf --service-name redis6380 --port 6380
redis-server --service-install redis.slave.6381.conf --service-name redis6381 --port 6381

rem install sentinel service
redis-server --service-install redis.sentinel1.conf --sentinel --service-name sentinel16379 --port 16379
redis-server --service-install redis.sentinel2.conf --sentinel --service-name sentinel26379 --port 26379
redis-server --service-install redis.sentinel3.conf --sentinel --service-name sentinel36379 --port 36379