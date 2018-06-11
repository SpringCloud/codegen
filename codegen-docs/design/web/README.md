# web接口设计

* 请求地址：http://ip:port/download
* 请求方式：POST
* 请求内容类型：application/json
* 请求内容(创建EurekaServer为例)：
```
    {
      "proType":"maven",
      "language":"java",
      "groupId":"com.test",
      "pomArtifactId":"eureka-server",
      "projectName":"eureka-server",
      "springBootVersion":"1.5.9",
      "javaVersion":1.8,
      "basePackage":"com.springcloud.component",
      "isDockerTemplate":true,
      "metadata":{"appType":"eureka-server",
                  "chooseComponent":["t1","t2"]}
      
     }
 ```
 #### 请求内容说明
 * `proType` 工程类型
 * `language` 语言
 * `groupId` groupId
 * `pomArtifactId` 生成工程名（服务名）、maven artifactId
 * `projectName` maven name、applicationName
 * `springBootVersion` Spring Boot版本，不带后缀 （现在可选1.4.7， 1.5.9， 2.0.0）
 * `javaVersion`  java版本 （可选1.6, 1.7， 1.8）
 * `basePackage` 上层包路径
 * `isDockerTemplate` docker模板
 * `appType` 应用类型（Eureka Server：eureka-server， Zuul Server：zuul-server， Config Server：config-server， Zipkin Server：zipkin-server，
                      (暂未开发)服务提供者：service-provider,  服务消费者：service-consumer, Spring Boot: spring-boot-server, war应用：war-server）
 *  `chooseComponent` 可选组件（暂未开发）（springMVC：spring-mvc, Mybatis: mybatis, Feign: feign, Hystrix: hystrix, Ribbon: ribbon）