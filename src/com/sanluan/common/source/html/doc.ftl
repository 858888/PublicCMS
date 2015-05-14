-- ${entityName} begin ${.now}---
字段列表：
<#list columnList as a>
	${a.name}:${a.title}
</#list>
menu项:
	<li><a href="${entityName?uncap_first}/list.html?navTabId=${entityName?uncap_first}" target="navTab" rel="${entityName?uncap_first}">管理</a></li>
自定义指令：
	分页列表查询：
		${r"<@d_"+entityName?replace('Cms','')?uncap_first+"List"} <#include "../include_condition/paramter.ftl">>${r"</@d_"+entityName?replace('Cms','')?uncap_first+"List>"}
		参数：
<#list conditionList as a>
		<#if "Date"=a.type>
			queryStart${a.name?cap_first}：开始${a.title}
			queryEnd${a.name?cap_first}：结束${a.title}
		<#else>
			query${a.name?cap_first}：${a.title}
		</#if>
</#list>
		结果：
			t_page:分页信息，totalCount：总条数，totalPage：总页数，pageSize每页数据条数，pageNo当前页数
			t_page.t_list: List<${entityName}>对象 使用<#noparse><#list t_list as a></#list></#noparse> 遍历数据
	单条记录查询：
		${"<@d_"+entityName?replace('Cms','')?uncap_first+" id=id></@d_"+entityName?replace('Cms','')?uncap_first+">"}
		结果：
			t_bean:属性参考字段列表
-- ${entityName} end --

