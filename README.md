# Trace Master

## 项目功能

### 管理端

1. 查看所有 trace master 实例，包括通过 javaagent 注入的、attach 到相应的 jvm 、trace-master-spring-boot-starter 等使用方式
2. 开启/关闭对应的 trace master 实例
3. 修改对应的 trace master 的配置
4. 进入对应的 trace master 实例，查询 h2 中的调用，并查看某次调用的调用链路以及耗时
5. 进入对应的 trace master 实例，通过 trace id 查询对应的 spring 项目的一次调用链路以及耗时

### trace master 实例

1. 与管理端建立长链，发送心跳，获取配置
2. 根据配置，对指定的类进行字节码增强，统计调用链路以及耗时，并生成单笔唯一的 trace id，上送给管理端
3. 只统计源代码耗时，不包括 trace master 内部耗时

模式分为 spring 模式以及 normal 模式

normal 模式：根据配置监听对应的方法

spring 模式：比 normal 模式多添加了一个配置，监听所有 controller 的方法

