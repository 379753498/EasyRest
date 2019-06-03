# easyrest

#### 介绍

easyrest 是一个由数据驱动自动化接口测试框架，
可以自动执行并生成报告。
自动化测试技术交流QQ群：31043004

### 近期更新（1.4.0发布）
- 1、接口Session 和Cookies 自动延续 到下一个接口可用 只需登录一次
- 2、后期如更换账号清除Session 和Cookies 配置ExecutionData.cleanCoockie=true
- 3、支持${request.headers.参数名}引用变量，支持函数${uuid} ${random(100)}动态获取参数。
- 4、美化报告可视化 记录请求参数和返回参数情况可以在allure报告中查阅。
### 计划增加功能
-  1、支持Xml验证、正则表达式验证方式。
-  2、支持测试数据记录到Mysql 可配置是否写入。

### 能力有限 水平一般 欢迎批评指正 PR
maven坐标
```
<dependency>
    <groupId>com.github.379753498</groupId>
    <artifactId>EasyRest</artifactId>
    <version>1.4.0</version>
</dependency>
```


#### 安装教程

若您想将功能集成到您的项目中，可引用maven依赖使用核心jar以便于快速使用该框架，

- 其中与SpirngBoot整合项目在https://gitee.com/testdevops/easyrest/tree/master/SpringBootDemo
- 其中与Spirng整合项目在https://gitee.com/testdevops/easyrest/tree/master/SpringDemo
- 其中与普通Maven项目在https://gitee.com/testdevops/easyrest/tree/master/MavenProjectDemo
  ``

```
<dependency>
    <groupId>com.github.379753498</groupId>
    <artifactId>EasyRest</artifactId>
    <version>1.4.0</version>
</dependency>
```

若您想详细了解框架如何运行的细节您可以这样做

1. 配置Java1.8、maven3.5.4环境、ecplise Idea 需安装插件 lombok、TestNg插件
2. 下载代码
3. 修改src/main/resources/ExcelData.xlsx中数据
4. 执行src/test/java/com/testpro/easyrest/EasyrestApplicationTests.java即可运行
5. 若想查看Alure报告 首先解压easyrest/src/test/resources/allure-2.7.0.zip到D:\盘根目录(随意哪里)
   - 环境变量Path中加入D:\allure-2.7.0\allure-2.7.0\bin;
   - 参考运行方式2执行  mvn test命令后
   - CMD命令行切换至项目根目录执行allure serve target/allure-results 此时浏览器就会自动打开执行后的报告；例如项目路径F:\WORKDEV\easyrest
   - 也可以在idea中的Terminal中执行allure serve target/allure-results 

#### 使用说明


#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
