<#ftl>
<#macro m code>${springMacroRequestContext.getMessage(code,code)}</#macro>
<#macro cut string l=50><#if string?length lt l>${string}<#else>${string[0..l-1]}</#if></#macro>
<#macro buildTree path>
	<@d_templateTree path=path>
		<#list t_list as a>
		<li>
			<#local queryPath=path+'/'+a.fileName>
			<#if a.isDirectory><a>${a.description!}</a>
				<ul>
					<@buildTree queryPath/>
				</ul>
			<#else>
				<a href="template/files.html?navTabId=files&queryPath=${queryPath}" target="navTab">${a.description!}</a>
			</#if>
		</li>
		</#list>
	</@d_templateTree>
</#macro>