创建工程：
（1）父：创建Empty project，然后新建最简单的pom（修改自己的group和工程名）
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
    <packaging>pom</packaging>
	<groupId>com.hckj</groupId>
	<artifactId>nls</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>nls</name>
	<description>nls</description>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencyManagement>
		<dependencies>
		</dependencies>
	</dependencyManagement>
</project>
（2）子：在工程里面新建module,spring initializr,java maven jar,spring boot 3.1.7(和pom里面的版本一致,我是3.4.0),勾选spring boot devtools（热部署）、lombok（简化实体类的编写）和spring web，点击always add
把生成的.gitignore文件复制到父目录下，全局生效。
在.idea/.gitignore右键，git，rollback，选择.idea目录下的所有xml，回滚，就会忽略，不会把没用的代码文件git到仓库。(运行子代码，需要启动lombok注解)
（3）父子关联（父pom安装到本地maven仓库mvn clean install，配置自己电脑的maven）：
    首先修改父pom，加parent和modules
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <modules>
        <module>business</module>
    </modules>
    然后修改子pom，改parent
    <parent>
        <groupId>com.hckj</groupId>
        <artifactId>nls</artifactId>
        <version>0.0.1-SNAPSHOT</version>
        <relativePath>../pom.xml</relativePath>
    </parent>
（4）代码git到本地仓库和远程仓库(首先删除掉子module里面的.git文件夹，我们只用父.git管理代码)
        #本地
        git init(初始化一个新的 Git 仓库)
        git add .(当前目录下的所有文件添加到 Git 暂存区)
        git commit -m "初始化项目或其他提交描述" (将暂存区的文件提交到本地仓库，并附上提交信息)
        git status(查看提交状态)
        git log(查看提交历史)
        git branch(查看当前分支)
        git checkout -b new-branch(切换到新分支)
        #一般远程主分支是ain，但本地一般是master，所以这里把master名字先修改成main
        git checkout master（确保当前位于 master 分支）
        git branch -m main （将 master 分支重命名为 main）
        #远程
        点击头像，settings，developer settings，personal access tokens，Tokens（classic），generate（仓库权限选择repo就可以）
        远程仓库的默认名称一般是 origin（git remote -v 查看远程仓库名称）
        git remote set-url 你的remote别名 https://你的token@你的仓库地址（将本地仓库与远程 GitHub 仓库关联起来）
        示例：git remote set-url origin https://ghp_ieS79@github.com/Turing-dz/Product_Development.git
        git push -u origin main/master（本地的 main 分支（或 master 分支）推送到远程仓库）
        后续代码修改后使用如下更新仓库
        git add .
        git commit -m "修改说明"
        git push
（5）使用idea自带的HTTP Client测试接口
    首先在business\src\main\java\com\hckj\business\controller\TestController.java里面写测试接口类
    @RestController注解(类注解@Controller 和 方法注解@ResponseBody 的组合，使标注类可以处理 HTTP 请求，并将返回结果直接写入 HTTP 响应体中)
        @Controller：表示该类是一个控制器类，用于处理请求并返回视图。
        @ResponseBody：表示方法返回的结果应该直接写入 HTTP 响应体，创建 RESTful API 服务，通常返回 JSON 或 XML 格式的数据，而不是视图(使用 @RestController 可以省略在每个方法上使用 @ResponseBody 注解的需要，从而简化开发。)
    然后在nls\http\test.http（以.http结尾）里面写测试,输入g，选择gtr（不带参数请求）生成代码模板，然后根据接口路径修改。
    最后进行测试，启动Application后端，区测试文件里点击三角运行符。
    #配置,获取并日志打印程序运行的环境变量
    在application.properties里面配置（server.port=18000服务启动端口，server.servlet.context-path=/nls服务访问路径前缀）
    在application类里面的启动方法里面通过.getEnvironment()方法环境对象（生成变量使用ctrl+alt+v），然后打印环境信息
    private static final Logger log = LoggerFactory.getLogger(BusinessApplication.class);
        public static void main(String[] args) {
            ConfigurableEnvironment environment = SpringApplication.run(BusinessApplication.class, args).getEnvironment();
            log.info("测试地址 http://localhost:{}{}/hello",environment.getProperty("server.port"),environment.getProperty("server.servlet.context-path"));
        }
（6）使用AOP打印并保存网络请求参数和返回数据的log
    首先在子pom里面添加dependency
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>
    然后在business\src\main\java\com\hckj\business\aspect\LogAspect.java类(添加@Aspect和@Component注解)里面定义基础的切点和三个切面方法
    前置通知@Before("切点方法名（）")，后置通知@After和环绕通知@Around
    Logger log = LoggerFactory.getLogger(getClass());
        /**
         * 定义切点（这里是controller里面所有的public方法）
         */
        @Pointcut("execution(public * com.hckj..*Controller.*(..))")
        public void pointe() {}
        @Before("pointe()")
        public void doBefore(){
            log.info("前置通知");
        }
        @After("pointe()")
        public void doAfter(){
            log.info("后置通知");
        }
        @Around("pointe()")
        public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
            log.info("环绕开始");
            Object result = proceedingJoinPoint.proceed();
            log.info("环绕结束");
            return result;
        }
    最后写具体的切面方法
        首先加包fastjson（进行 JSON数据的序列化和反序列化）
        在父pom里面添加dependency
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.70</version>
        </dependency>
        在子pom里面添加dependency
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
        </dependency>
        然后环绕通知里写切面业务
        @Around("pointe()")
            public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
                log.info("----------------- 环绕通知开始  -----------------");
                long startTime = System.currentTimeMillis();
                // 开始打印请求日志
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                String signature = proceedingJoinPoint.getSignature().toString();
                String name = signature;
                // 打印请求信息
                log.info("请求地址: {}", request.getRequestURL().toString(), request.getMethod());
                log.info("类名方法: {}", signature, name);
                log.info("远程地址: {}", request.getRemoteAddr());
                // 打印请求参数
                Object[] args = proceedingJoinPoint.getArgs();
                // 排除特殊类型的参数，如文件类型
                Object[] arguments = new Object[args.length];
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof ServletRequest
                            || args[i] instanceof ServletResponse
                            || args[i] instanceof MultipartFile) {
                        continue;
                    }
                    args[i] = args[i];
                }
                // 排除字段，敏感字段或太长的字段不显示：身份证、手机号、邮箱、密码等
                String[] excludeProperties = {"cvv2", "idCard"};
                PropertyPreFilters filters = new PropertyPreFilters();
                PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filters.addFilter();
                excludeFilter.addExcludes(excludeProperties);
                log.info("请求参数：{}", JSONObject.toJSONString(arguments, excludeFilter));
                Object result = proceedingJoinPoint.proceed();//执行目标方法,切点的处理过程
                log.info("返回结果：{}", JSONObject.toJSONString(result, excludeFilter));
                log.info("----------------- 环绕通知结束 耗时：{} ms -----------------", System.currentTimeMillis() - startTime);
                return result;
            };











