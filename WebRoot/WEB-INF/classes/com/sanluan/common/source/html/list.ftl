${"<@d_"+entityName?replace('Cms','')?uncap_first+"List"} <#include "../include_condition/paramter.ftl">>
<form id="pagerForm" method="post">
	<#noparse><#include "../include_page/queryParamters.html"/></#noparse>
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			<#list conditionList as a><#if "Date"=a.type>
				<td>
					<label>${a.title}：</label>
					<input type="text" name="queryStart${a.name?cap_first}" class="date" dateFmt="yyyy-MM-dd" maxDate="{%y}-%M-{%d}" value="$${"{queryStart"+a.name?cap_first+"!}"}" />
					-
					<input type="text" name="queryEnd${a.name?cap_first}" class="date" dateFmt="yyyy-MM-dd" maxDate="{%y}-%M-{%d}" value="$${"{queryEnd"+a.name?cap_first+"!}"}" />
				</td>
				<#else>
				<td>
					${a.title}：<input type="text" name="query${a.name?cap_first}" value="$${"{query"+a.name?cap_first+"!}"}" />
				</td>
			</#if></#list>
			</tr>
		</table>
		<#noparse><#include "../include_page/searchBar.html"/></#noparse>
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="${entityName?uncap_first}/add.html" target="dialog"><span>添加</span></a></li>
			<li><a class="edit" href="${entityName?uncap_first}/add.html?navTabId=<#noparse>${navTabId!}</#noparse>&id={sid}" target="dialog"><span>修改</span></a></li>
			<li><a class="delete" href="${entityName?uncap_first}/delete.do?id={sid}" title="确定要删除该条记录吗?" target="ajaxTodo"><span>删除</span></a></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="137">
		<thead>
			<tr>
				<#list columnList as a>
				<th<#if a.order> orderField="${a.name}" class="<#noparse><#if orderField??&&</#noparse>'${a.name}'<#noparse>==orderField><#if 'asc'=orderType>asc<#else>desc</#if><#else>order</#if></#noparse>"</#if>>${a.title}</th>
				</#list>
			</tr>
		</thead>
		<tbody>
			<#noparse><#list t_page.list as a>
			<tr target="sid" rel="${a.id}">
			</#noparse>
				<#list columnList as a>
				<td>$${"{a."+a.name+"!}"}</td>
				</#list>
			<#noparse>
			</tr>
			</#list></#noparse>
		</tbody>
	</table>
<#noparse><#include "../include_page/page.html"/></#noparse>
</div>
${"</@d_"+entityName?replace('Cms','')?uncap_first+"List>"}