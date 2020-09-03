
#### 项目介绍

![sojpt-logo](http://www.sojpt.com//upload/img/sojpt-logo.png)

**基于Jfinal的web脚手架** 

SoJpt 是具有如下: 

用户管理

角色管理(重新定义角色管理容部门单位等父子结构与一体的角色管理)

权限管理(基于url的权限,不仅实现权限uri的精确匹配,同时也支持模糊匹配)

数据库监控(基于druid的sql以及session监控)

web综合监控(基于JavaMelody)

灵活可配置的审计功能(通过权限配置是否审计即可对相应模块进行审计,无需写代码实现)

动态父子结构菜单,包括菜单可动态配置

禁止一个用户多客户端同时登陆, 如果用户重复登陆会强制下线已经登陆的用户

支持多次密码输入错误,锁定用户功能,默认15分钟后自动解锁.

用Soutils代码生成工具可快速生成从前台到后台的一系列代码

#### 官网地址

[www.sojpt.com](http://sojpt.com)

## 效果展示

- 在线预览地址 (http://www.sojpt.com:8088/index.html)

<img src="http://files.git.oschina.net/group1/M00/07/4F/PaAvDFyxmh2AI9qrAAHgh2_oqbM530.png?token=d8e5e6855a2accdbb0f4476bd874acd0&ts=1555143197&attname=sojpt-home.png&disposition=inline" align=center />

#### 软件架构

SoJpt 脚手架 基于 SoJpt Boot + SoJpt-iview-admin + mysql 实现.

该项目为SoJpt Boot官方最佳实践

#### 安装教程

1. 从项目根路径sql文件夹中找到sql文件，创建数据库。
2. 修改jdbc_config_dev.properties文件中的数据库连接信息


#### 使用说明

1. 运行 /SoJpt/src/main/java/com/sohnny/MainConfig.java 文件

2. 部署前台SoJpt-iview-admin,并启动

3. 打开 http://127.0.0.1/admin 即可登陆项目 root:000000, admin:000000

#### 1.2 changeLog

1.加入JFinal Undertow的打包方式，极速打包发布

2.druid配置使用Spring Boot原生配置, 可查看spring监控

3.升级为SoJpt Boot 2.5.4

4.修复重复登录拦截验证失效问题

5.修复登录次数验证失效问题

6.加入热加载报异常解决方案-/src/main/resources/META-INF/spring-devtools.properties

7.日志列表取消userId的显示

8.修改 to_date 为 str_to_date 函数

9.jdbcUrl追加serverTimezone=Asia/Shanghai，避免使用新版mysql时区问题


#### 1.2 SoJpt Boot-iview-admin changeLog

1.日志列表取消userId的显示

2.修复重复登录拦截验证失效问题

3.修复角色赋权列表不显示以赋权且没子节点的菜单

#### 1.0 SoJpt Boot-iview-admin changeLog

1.改为前后端分离，保留后端服务代码，前台改为vue版, 后台SoJpt Boot

#### 1.0 changeLog

1.取消url传参方式, 不建议url传参, 避免增加权限配置复杂度
2.升级为jfinall3.7
3.改善审计记录参数功能
4.修复菜单根节点赋权bug

#### 0.3 changeLog

1.修复IE浏览器权限缓存问题，改为不缓存。

2.加入hcharts组件包，实现简单的后主页统计

#### 0.3 changeLog

1.增加超级用户ROOT, 管理员, 普通用户代码区 如下:

if (isRoot()) {         
    sqlPara = Db.getSqlPara("admin_role.root_select", getKv(dicRole));
} else if (isAdmin()) {
    sqlPara = Db.getSqlPara("admin_role.select", getKv(dicRole));           
} else {
    //普通用户
}
2. 完美兼容IE8+

#### 0.2 changeLog

1. 加入权限同步功能

#### 0.1 正式发布
