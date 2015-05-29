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
<#macro categoryTree file target queryCategoryId='' paramters='' queryParentId=''>
	<@d_categoryList parentId=queryParentId isDisable=false count=100>
		<#if t_page.list?has_content>
		<#list t_page.list as a>
		<li>
			<a href="${file}?queryCategoryId=${a.id}&${paramters!}" <#if queryCategoryId??&&a.id?string=queryCategoryId>class="selected"</#if> target="ajax" rel="${target}">${a.name!}</a>
		<#if a.childIds?has_content>
			<ul>
				<@categoryTree file target queryCategoryId paramters a.id/>
			</ul>
		</#if>
		</li>
		</#list>
		<#else>
			<li><a>空目录</a></li>
		</#if>
	</@d_categoryList>
</#macro>
<#macro input extend value>
<#switch extend.inputType>
	<#case 'number'>
		<input class="number" name="${extend.code}" type="text" size="30" value="${value!extend.defaultValue!}"/>
		<#break>
	<#case 'file'>
		<input class="readonly" name="fileName" value="${value!extend.defaultValue!}" readonly="readonly" type="text"/>
		<a class="btnAttach" href="file/upload.html" lookupGroup="" width="560" height="300" title="附件">附件</a>
		<#break>
	<#case 'password'>
		<input class="required" name="${extend.code}" type="password" size="30" value="${value!extend.defaultValue!}"/>
		<#break>
	<#case 'textarea'>
		<textarea name="${extend.code}" class="required" cols="80" rows="4">${value!extend.defaultValue!}</textarea>
		<#break>
	<#default>
		<input class="required" name="${extend.code}" type="text" size="30" value="${value!extend.defaultValue!}"/>
</#switch>
</#macro>