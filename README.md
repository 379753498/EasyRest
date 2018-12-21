# easyrest

#### 介绍
easyrest 是一个由数据驱动自动化接口测试框架，
可以自动执行并生成报告。



#### 软件架构
软件架构说明



#### 安装教程

1. 配置Java1.8、maven3.5.4环境、ecplise Idea 需安装插件 lombok、TestNg插件
2. 下载代码
3. 修改src/main/resources/ExcelData.xlsx中数据
4. 执行src/test/java/com/testpro/easyrest/EasyrestApplicationTests.java即可运行

#### 使用说明

1. ExcelData.excel填写规范
    - 用例名称：{字符串、无长度限制，非必填项}
    - 用例描述：{字符串、无长度限制，推荐填写}
    - 地址：{字符串、有长度限制取决于Get规范，必须填写}

     支持写法1、填写全url路径 例如http://www.baidu.com/hello

     支持写法2、填写请求路径/hello，此种写法需要在src/main/resources/application.properties中补充属性easyrest.restassured.baseurl=http://www.baidu.com

    - 头信息:{Json字符串、无长度限制，非必填项}
    - 参数：{Json字符串、有长度限制取决于Get规范，非必填项}
    - 方法：{字符串、暂只支持get/post，必填项}
    - 返回值类型:{字符串、json，必填项}
    - 返回值校验:{Json字符串、无长度限制，非必填项}
    - josnPath校验：{Json字符串、无长度限制，非必填项}

     此处Key需满足jsonpath语法 https://testerhome.com/topics/6672

    - 字符包含校验:{字符串、无长度限制，非必填项，分隔符为英文 , }
2. 运行方式
     -  执行src/test/java/com/testpro/easyrest/EasyrestApplicationTests.java即可运行
     - 执行maven 命令 mvn test（不懂如何执行请及时百度）

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request
