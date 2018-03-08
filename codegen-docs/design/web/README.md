# web接口设计

* 请求地址：http://ip:port/download
* 请求方式：POST
* 请求内容类型：application/json
* 请求内容(创建EurekaServer为例)：
```
    {
         "pomArtifactId":"eureka-server",
         "moduleName":"eureka-server",
         "pomName":"eureka-server",
         "springBootVersion":"1.5.9",
         "javaVersion":1.8,
         "basePackage":"com.springcloud.component",
         "scAloneRadio":"eureka-server"
     }
 ```
 #### 请求内容说明
 * `pomArtifactId` maven artifactId
 * `moduleName` 生成工程名（服务名）
 * `pomName` maven name
 * `springBootVersion` Spring Boot版本，不带后缀 （现在可选1.4.7， 1.5.9， 2.0.0）
 * `javaVersion`  java版本 （可选1.6, 1.7， 1.8）
 * `basePackage` 上层包路径
 * `scAloneRadio` 独立组件（[组件名：`scAloneRadio` 的value值]，Eureka Server：eureka-server， Zuul Server：zuul-server， Config Server：config-server， Zipkin Server：zipkin-server）
 