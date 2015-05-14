read me

1. 文档Change Log.txt,Code Formatter.xml,Database Change Log.sql,分别为软件变更记录,代码格式化配置文件,数据库变更记录

2. 软件变更记录：中记录功能模块的新增,BUG,调整

3. 代码格式化配置文件：导入到eclipse中使用,操作步骤:Window->Preferences->Java->Code Style->Formatter->Import,使用快捷键alt+shift+j添加备注,ctrl+shift+f格式化代码

4. 数据库变更记录： 记录数据库库表结构变更、初始化数据变更SQL语句

5. 软件基础架构：软件使用了SpringMVC 4.1.4.RELEASE,Hibernate 4.3.8.Final,Mysql jdbc5.1.35,c3p0 0.9.1.2,dom4j 1.6.1,log4j+bogback,Apache commons,freemarker 2.3.22包

6. 目录与文件说明：
	hibernate	hibernate实体生成工具配置文件存放目录
	src			java源码
	WebRoot		web文件
		error		自定义HTTP错误页面文件目录
		META-INF	META-INF信息目录
		resource	资源文件目录
		WEB-INF		模板，类库，等文件目录
			config		配置文件目录
				spring		框架配置目录
				dbconfig.properties		数据库配置文件
			language	语言文件目录
			template	模板文件目录
				ftl			freemaker模板宏定义文件目录
			web.xml		web项目描述文件

7. 包命名规范：
	.common	工具类、解决方案类等同用类包
		.base			基础类包
		.constants		常量定义类包
		.dialect		数据库方言类包
		.handler		分页处理，查询处理，FreeMarker异常等处理器包
		.servlet		Servlet包
		.source			源码工具包
		.tools			工具类包
	.entities	实体类包
	.logic	逻辑类包
		.component	组件包
		.dao		数据库操作类包
		.service	事务与逻辑封装类包
		.tasks	任务计划包
	.views	视图类包
		.controller	控制器类包
			Index.java	同用分发Controller类
		.directive	freemaker自定义指令类包
		.method		freemaker自定义方法类包

8. 开发模式：
	controller主要用于表单处理等逻辑，freemaker用于展现部分，以html为基础，通过使用freemaker模板标签等实现大部分功能类需求。
	展现中的字段关联行为尽量使用模板自定义指令嵌套查询，将大部分的关联查询分解为简单查询。必要时使用freemaker生成页面功能将部分页面静态化以混存耗时较大的数据

9. 开发流程：
	1. 在开发工具中使用hibernate工具生成实体类，修改数据库配置文件
	2. 对实体类属性使用com.sanluan.common.source.entity.MyColumn注解类标记字段类型【condition：是否用于查询条件，order：是否用于排序，like：查询条件是否为like匹配，or：查询条件是否为or逻辑，name：字段名，当or为true时，相同name 的字段为同一组or条件，title：字段真实含义】
	3. 使用com.sanluan.common.source.Maker工具类 生成自定义指令，Controller，Service，Dao，Html
	4. 根据实际业务调整代码中的逻辑
	
10. 其他规范：
	页面文件使用.html做后缀
	表单请求使用.do做后缀
