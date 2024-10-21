# Trace Master

## 项目功能

### trace-master-server

1. 实例管理
   1. 查看所有 trace master 实例信息
   2. 启停 trace master 实例

2. 配置管理
   1. 查看所有配置
   2. 修改配置
   3. 分发配置

3. 调用链路管理
   1. 查看所有调用链路
   2. 查看链路详情


### trace-master-agent

1. 与管理端建立长链，发送心跳，获取配置
2. 根据配置，对指定的类进行字节码增强，统计调用链路以及耗时，并生成单笔唯一的 trace id，上送给管理端
3. 只统计源代码耗时，不包括 trace master 内部耗时

模式分为 spring 模式以及 normal 模式

normal 模式：根据配置监听对应的方法

spring 模式：比 normal 模式多添加了一个配置，监听所有 controller 的方法

