<#ftl>
<#macro m code>${springMacroRequestContext.getMessage(code,code)}</#macro>
<#macro cut string l=50><#if string?length lt l>${string}<#else>${string[0..l-1]}</#if></#macro>
<#macro buildTree dir filepath>
	<@d_templateTree path=filepath>
		<#if t_list?has_content>
		<#list t_list as a>
		<li>
			<#local queryPath=filepath+'/'+a.fileName>
			<#if a.isDirectory><a href="${dir}/list.html?queryPath=${queryPath}" target="navTab" rel="${dir}" >${a.description!}</a>
				<ul>
					<@buildTree dir queryPath/>
				</ul>
			<#else>
				<a href="${dir}/list.html?queryPath=${queryPath}" target="navTab" rel="${dir}">${a.description!}</a>
			</#if>
		</li>
		</#list>
		<#else>
			<li><a>空目录</a></li>
		</#if>
	</@d_templateTree>
</#macro>