# unifymq
one mq for product ready, easy to use,ops support,monitor support,security support


Nacos Spring Project
=================================

[![Build Status](https://travis-ci.org/nacos-group/nacos-spring-project.svg?branch=master)](https://travis-ci.org/nacos-group/nacos-spring-project)

[Unifymq](https://github.com/zyhui98/unifymq) is an open source project for discovering, configuring and managing cloud-native applications. Key features of Nacos include:

- Mq Discovery and Mq Health Check
- Dynamic Configuration Management
- Dynamic DNS Service
- Service and Metadata Management

[Unifymq Spring Project](https://github.com/zyhui98/unifymq-spring-project), , fully embraces the Spring ecosystem and is designed to help you build Spring applications rapidly. 

The project contains a core module named [`unifymq-spring-context`](nacos-spring-context). It enables you to expand modern Java programming models in the following ways:

- [Annotation-Driven](#41-annotation-driven)

These features strongly depend on Spring Framework 3.2+ API, and can be seamlessly integrated with any Spring Stack, such as Spring Boot and Spring Cloud.

**Note:** We recommend that you use annotation-driven programming, even though XML-based features also work.

Content
===============================
<!-- TOC -->

- [1. Samples](#1-samples)
    - [1.1. Samples](#11-samples)
    - [1.2. How To Run the Samples](#12-how-to-run-the-samples)
- [2. Dependencies & Compatibility](#2-dependencies--compatibility)
- [3. Quickstart](#3-quickstart)

<!-- /TOC -->


# 1. Samples

Included in this section are some samples for you to get a quick start with Nacos Spring.

## 1.1. Samples

- [Dependency Injection Sample of `@NacosInjected`](https://github.com/nacos-group/nacos-spring-project/blob/master/nacos-spring-samples/nacos-spring-webmvc-sample/src/main/java/com/alibaba/nacos/samples/spring/NacosConfiguration.java)

## 1.2. How To Run the Samples

Take the Spring Web MVC project for example:

1. Check out the source code of `unifymq-spring-project` :
   
	` $ git clone git@github.com:zyhui98/unifymq-spring-project.git`

2. Build your source code with Maven:
    
	`$ mvn clean package`
    
3. Run Spring Web MVC Samples:

      `$ java -jar target/unifymq-spring-webmvc-sample.war`

# 2. Dependencies & Compatibility

The following table shows the dependencies and compatabilities of Nacos Spring Project.

| Dependencies   | Compatibility |
| -------------- | ------------- |
| Java           | 1.6+         |
| Spring Context | 3.2+         |



# 3. Quickstart

This quickstart shows you how to enable Nacos and its service discovery and configuration management features in your Spring project.

## 3.1. Prerequisite

Before you configure your Spring project to use Nacos, you need to start a Nacos server in the backend. Refer to [Nacos Quick Start](https://nacos.io/en-us/docs/quick-start.html) for instructions on how to start a Nacos server.

## 3.2. Enable Unifymq
Complete the following steps to enable Nacos for your Spring project.

1. Add [`unifymq-spring-context`](unifymq-spring-context) in your Spring application's dependencies:

	```xml
	    <dependencies>
	        ...
	        
	        <dependency>
	            <groupId>com.alibaba.zyhui98</groupId>
	            <artifactId>unifymq-spring-context</artifactId>
	            <version>0.0.1</version>
	        </dependency>
	        
	        ...
	    </dependencies>
	```

**Note:** Support Spring 5 from version 0.2.3-RC1.

2. Add the `@EnableNacos` annotation in the `@Configuration` class of Spring and specify "\${host}:${port}" of your Nacos server in the `serverAddr` attribute:

	```java
	@Configuration
	@EnableNacos(
	        globalProperties =
	        @NacosProperties(serverAddr = "${nacos.server-addr:localhost:12345}")
	)
	public class NacosConfiguration {
	    ...
	}
	```

For details about the usages of `ConfigService` and `NamingService`, please refer to [Nacos SDK](https://nacos.io/en-us/docs/sdk.html).



# 4. Core Features

This section provides a detailed description of the key features of [`nacos-spring-context`](nacos-spring-context):

- [Annotation-Driven](#annotation-driven)
- [Dependency Injection](#dependency-injection)
- [Externalized Configuration](#externalized-configuration)
- [Event-Driven](#eventlistener-driven)



## 4.1. Annotation-Driven

### 4.1.1. Enable Nacos

`@EnableNacos` is a modular-driven annotation that enables all features of Nacos Spring, including **Service Discovery** and **Distributed Configuration**. It equals to  `@EnableNacosDiscovery` and 
`@EnableNacosConfig`, which can be configured separately and used in different scenarios.

### 4.1.2. Configure Change Listener method

Suppose there was a config in Nacos Server whose `dataId` is "testDataId" and `groupId` is default group("DEFAULT_GROUP"). Now you would like to change its content by using the `ConfigService#publishConfig` method:

#### 4.1.2.1. Type Conversion

The type conversion of `@NacosConfigListener` includes both build-in and customized implementations. By default, build-in type conversion is based on Spring `DefaultFormattingConversionService`, which means it covers most of the general cases as well as the rich features of the higher Spring framework. 


#### 4.1.2.2. Timeout of Execution

As it might cost some time to run customized `NacosConfigConverter`, you can set  max execution time in the `@NacosConfigListener.timeout()` attribute to prevent it from blocking other listeners:



### 4.1.4. `@NacosProperties`

`@NacosProperties` is a uniform annotation for global and special Nacos properties. It serves as a mediator between Java `Properties` and `NacosFactory` class.   `NacosFactory` is responsible for creating `ConfigService` or `NamingService` instances. 

The attributes of `@NacosProperties` completely support placeholders whose source is all kinds of `PropertySource` in Spring `Environment` abstraction, typically Java System `Properties` and OS environment variables. The prefix of all placeholders are `nacos.`.  The mapping between the attributes of `@NacosProperties` and Nacos properties are shown below: 

| Attribute       | Property       | Placeholder              | Description | Required  |
| --------------- | -------------- | ------------------------ | ----------- | --------- |
| `endpoint()`    | `endpoint`     | `${nacos.endpoint:}`     |             |     N     |
| `namespace()`   | `namespace`    | `${nacos.namespace:}`    |             |     N     |
| `accessKey()`   | `access-key`   | `${nacos.access-key:}`   |             |     N     |
| `secretKey()`   | `secret-key`   | `${nacos.secret-key:}`   |             |     N     |
| `serverAddr()`  | `server-addr`  | `${nacos.server-addr:}`  |             |     Y     |
| `contextPath()` | `context-path` | `${nacos.context-path:}` |             |     N     |
| `clusterName()` | `cluster-name` | `${nacos.cluster-name:}` |             |     N     |
| `encode()`      | `encode`       | `${nacos.encode:UTF-8}`  |             |     N     |





## 4.2. Dependency Injection

`@NacosInjected` is a core annotation which is used to inject `ConfigService` or `NamingService` instance in your Spring Beans and make these instances **cacheable**. This means the instances will be the same if their `@NacosProperties` are equal, regargless of whether the properties come from global or special Nacos properties:



- See [Dependency Injection Sample](https://github.com/nacos-group/nacos-spring-project/blob/master/nacos-spring-samples/nacos-spring-webmvc-sample/src/main/java/com/alibaba/nacos/samples/spring/NacosConfiguration.java)




## 4.3. Externalized Configuration

Externalized configuration is a concept introduced by Spring Boot, which allows applications to receive external property sources to control runtime behavior. Nacos Server runs an isolation process outside the application to maintain the application configurations. [`nacos-spring-context`](nacos-spring-context) provides properties features including object binding, dynamic configuration(auto-refreshed) and so on, and dependence on Spring Boot or Spring Cloud framework is required.

- See [Auto-Refreshed Sample of `@NacosConfigurationProperties`](https://github.com/nacos-group/nacos-spring-project/blob/master/nacos-spring-samples/nacos-spring-webmvc-sample/src/main/java/com/alibaba/nacos/samples/spring/properties/NacosConfigurationPropertiesConfiguration.java)
- See [Sample of `@NacosPropertySources` and `@NacosPropertySource`](https://github.com/nacos-group/nacos-spring-project/blob/master/nacos-spring-samples/nacos-spring-webmvc-sample/src/main/java/com/alibaba/nacos/samples/spring/env/NacosPropertySourceConfiguration.java)



## 4.4. Event/Listener Driven

Nacos Event/Listener Driven is based on the standard Spring Event/Listener mechanism. The `ApplicationEvent` of Spring is an abstract super class for all Nacos Spring events:

| Nacos Spring Event                           | Trigger                                                      |
| -------------------------------------------- | ------------------------------------------------------------ |
| `NacosConfigPublishedEvent`                  | After `ConfigService.publishConfig()`                        |
| `NacosConfigReceivedEvent`                   | After`Listener.receiveConfigInfo()`                          |
| `NacosConfigRemovedEvent`                    | After `configService.removeConfig()`                         |
| `NacosConfigTimeoutEvent`                    | `ConfigService.getConfig()` on timeout                       |
| `NacosConfigListenerRegisteredEvent`         | After `ConfigService.addListner()` or `ConfigService.removeListener()` |
| `NacosConfigurationPropertiesBeanBoundEvent` | After `@NacosConfigurationProperties` binding                |
| `NacosConfigMetadataEvent`                   | After Nacos Config operations                                |

- See [Event/Listener Sample](https://github.com/nacos-group/nacos-spring-project/blob/master/nacos-spring-samples/nacos-spring-webmvc-sample/src/main/java/com/alibaba/nacos/samples/spring/event/NacosEventListenerConfiguration.java)



# 5. Modules

- [`unifymq-spring-context`](unifymq-spring-context)
  
- [`unifymq-spring-samples`](unifymq-spring-samples)



# 6. Relative Projects

- [Alibaba Spring Context Support](https://github.com/alibaba/spring-context-support)
