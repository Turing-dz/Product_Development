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
    #目前github使用 HTTPS 进行身份验证时，需要使用 Personal Access Token (PAT)认证，具体可以参考博文https://blog.csdn.net/weixin_56176015/article/details/129833353
    远程仓库的默认名称一般是 origin（git remote -v 查看远程仓库名称）
    git remote set-url 你的remote别名 https://你的token@你的仓库地址（将本地仓库与远程 GitHub 仓库关联起来）
    示例：git remote set-url origin https://github_pat_55JVU10fmK2@github.com/Turing-dz/Product_Development.git
    git push -u origin main/master（本地的 main 分支（或 master 分支）推送到远程仓库）
    后续代码修改后使用如下更新仓库
    git add .
    git commit -m "修改说明"
    git push

（5）








