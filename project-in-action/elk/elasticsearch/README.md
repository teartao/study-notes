# elasticsearch配置

主配置

```yaml
cluster.name: es-app
node.name: es-master
node.master: true
network.host: 127.0.0.1
# http.port: 9200   默认web端口
# 9300 默认API端口
追加如下内容
http.cors.enabled: true #允许跨域 
http.cors.allow-origin: "*"
```

从配置

```yaml
 cluster.name: es-app
 node.name: es-slave
 network.host: 127.0.0.1
 http.port: 8200  # 注意与默认端口9200 9300 区分防止冲突
 discovery.zen.ping.unicast.hosts: ["127.0.0.1"]
```



 elasticsearch-head插件配置

```shell
git clone git://github.com/mobz/elasticsearch-head.git
cd elasticsearch-head
npm install
npm run start
```



