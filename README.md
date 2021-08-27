# smartline(米线)

**米线为个人学习的即时通讯开源App，依照大厂编码规范，在项目中反复思考并重构**

您如果需要使用仓库源代码打包，需要根据文末的指引配置相关参数，否则将无法使用联网功能

与该App配套的服务端代码仓库为 [smartservice](https://github.com/zpsong-tower/smartservice)

功能与版本计划：

- 账号注册与登录 - 0.1
- 添加与搜索好友 - 0.2
- 个人信息展示 - 0.3
- 一对一聊天 - 0.5 - [Demo下载](https://towerdance.oss-cn-shanghai.aliyuncs.com/github/smartline/smartline-0.5.0.apk)
- 最近会话 - 0.6
- 发送图片 - 0.7
- 发送语音 - 0.8
- 群组模块 - 0.9
    - 群聊
    - 创建群
    - 权限管理
    - 成员管理
- 检查更新 - 1.0
- QQ表情引入 - 1.1
- 聊天记录查询 - 1.2
- 语音通话 2.0
- 视频通话 2.5
- 社区模块 3.0
- 短视频 3.5
- 其他：插件化改造

---

**参数配置**

在 `Config.java` 中配置远程服务器地址及[阿里云OSS](https://www.aliyun.com/product/oss)的相关参数：

```java
/**
 * SmartService 网络请求地址
 */
public static final String API_URL = "";

/**
 * 阿里云OSS 终端地址
 */
public static final String OSS_ENDPOINT = "";

/**
 * 阿里云OSS 访问秘钥Id
 */
public static final String OSS_ACCESS_KEY_ID = "";

/**
 * 阿里云OSS 访问秘钥密码
 */
public static final String OSS_ACCESS_KEY_SECRET = "";

/**
 * 阿里云OSS 仓库名
 */
public static final String OSS_BUCKET_NAME = "";
```

在 `build.gradle (:app)` 中配置[个推](https://www.getui.com)的相关参数

```groovy
manifestPlaceholders = [
        GETUI_APP_ID    : "需要开发者自行配置",
        GETUI_APP_KEY   : "需要开发者自行配置",
        GETUI_APP_SECRET: "需要开发者自行配置",
        PACKAGE_NAME    : "需要开发者自行配置"
]
```

