
springBoot的`helloworld`项目

1.脚手架
- 熟悉项目框架起步

2.整合mybatis  
- 配置mybatis
- 自定义配置数据库连接池druid，以及druid监控后台的使用
- mybatis注解方式写sql语句以及xml中写sql语句
- 声明式事务 EnableTransactionManagement


3.整合三个web组件
- @ServletComponentScan // 开启扫描三大组件的注解
- filter过滤器的使用
- servlet【这个基本工作用不上】
- listen监听器的使用


记一个错误
```$xslt
<mapper namespace="cn.tanglaoer.springboot.mapper.TAdminMapper">
```
当时没有看 `名称空间`,发现总说找不到 mapper接口，各种配置中找。。。。，结果才知道 `namespace`没有写对


