# JSON Web Token Redis令牌

## 使用方式

### 1. 引用jar

    <dependency>
        <groupId>com.framework.token</groupId>
        <artifactId>jwtr-spring-boot-starter</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
    
## 2. 环境

    jdk : 1.8.0_201-b09
    springboot : 2.1.7.RELEASE
    
使用JWTR 需要配置Redis

    spring:
      redis:
          host: 
          port: 6379
          password: 
          timeout: 1000
          database: 1
          pool:
            max-active: 300
            max-idle: 100
            min-idle: 100
            max-wait: 1000
      session:
        store-type: redis   
    
## 3. 签名验证api接口

可使用@ApiController注解代替@RestController来提供对外接口，默认需要签名‘sign’参数。

POST:
	
	@PostMapper("/test")
	public void test(@RequestBody User user);
User对象必须包含时间戳和签名字段：

	private String timestamp,//时间戳
    private String sign;//签名
Restfull:

	@GetMapper("/test/{id}/{timestamp}/{sign}")
	public void test(@PathVariable int id,@PathVariable String timestamp,@PathVariable String sign);
类不需要签名：

	@ApiController(sign = false)

方法不需要签名：

	@NotSign
	@PostMapper("/test")
	public void test(@RequestBody User user);
	
## 4. 公用验证码接口

只需在启动类加  `@EnableKaptcha`  注解即可。

验证码接口访问：

	http://localhost:[port]/verification	
	
## 5. JWTR Token验证

用 `@TokenController` 替换 `@RestController` 实现 Controller 类全方法Token验证， 请求时在http header里加上token参数即可。

`@TokenPass` 注解和 `@TokenController` 联合使用，在 `@TokenController`注解的类里若某方法不需要token验证则可使用 `@TokenPass` 排除。

`@TokenEnable` 注解使用于某方法，和 `@TokenController` 作用相同，`@TokenEnable`方法注解 `@TokenController` 类注解。

获取当前登录用户：

	TokenAuthUtil.getCurrentUser();	