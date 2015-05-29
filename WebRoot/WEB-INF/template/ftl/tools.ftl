<#ftl>
<#macro m code>${springMacroRequestContext.getMessage(code,code)}</#macro>
<#macro cut string l=50><#if string?length lt l>${string}<#else>${string[0..l-1]}</#if></#macro>
<#macro templateTree dir filepath>
	<@d_templateTree path=filepath>
		<#if t_list?has_content>
		<#list t_list as a>
		<li>
			<#local queryPath=filepath+'/'+a.fileName>
			<a href="${dir}/list.html?queryPath=${queryPath}" target="navTab" rel="${dir}" >${a.description!}</a>
		<#if a.isDirectory>			
			<ul>
				<@templateTree dir queryPath/>
			</ul>
		</#if>
		</li>
		</#list>
		<#else>
			<li><a>空目录</a></li>
		</#if>
	</@d_templateTree>
</#macro>
<#macro categoryTree queryCategoryId='' paramters='' queryParentId=''>
	<@d_categoryList parentId=queryParentId isDisable=false count=100>
		<#if t_page.list?has_content>
		<#list t_page.list as a>
		<li>
			<a href="cmsContent/_lookup.html?queryCategoryId=${a.id}&${paramters!}" <#if queryCategoryId??&&a.id?string=queryCategoryId>class="selected"</#if> target="ajax" rel="contentSelectBox">${a.name!}</a>
		<#if a.childIds?has_content>
			<ul>
				<@categoryTree queryCategoryId paramters a.id/>
			</ul>
		</#if>
		</li>
		</#list>
		<#else>
			<li><a>空目录</a></li>
		</#if>
	</@d_categoryList>
</#macro>