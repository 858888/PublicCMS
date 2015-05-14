<#list conditionList as a>
	<#if "Date"=a.type>
		if (notEmpty(start${a.name?cap_first})) {
			queryMaker.condition("bean.${a.name} >= :start${a.name?cap_first}").setParameter("start${a.name?cap_first}", start${a.name?cap_first});
		}
		if (notEmpty(end${a.name?cap_first})) {
			queryMaker.condition("bean.${a.name} < :end${a.name?cap_first}").setParameter("end${a.name?cap_first}", tomorrow(end${a.name?cap_first}));
		}
	<#else>
		if (notEmpty(${a.name})) {
			queryMaker.condition("<@condition a/>").setParameter("${a.name}", <#if "String"=a.type&&a.like>like(${a.name})<#else>${a.name}</#if>);
		}
	</#if>
</#list>
<#list columnList as a><#if a.order><#assign order=true/><#break/></#if></#list>
<#if order??&&order>
		if("asc".equals(orderType)){
			orderType = "asc";
		}else{
			orderType = "desc";
		}
		if(!notEmpty(orderField)){
			orderField="";
		}
		switch(orderField) {
		<#list columnList as a><#if a.order>
			case "${a.name}" : queryMaker.append("order by bean.${a.name} " + orderType); break;
		</#if></#list>
			default : queryMaker.append("order by bean.id "+orderType);
		}
<#else>
		queryMaker.append("order by bean.id desc");
</#if>
<#macro condition a><#if a.or>(</#if><#list a.nameList as n>bean.${n} <#if "String"=a.type&&a.like>like<#else>=</#if> :${a.name}<#if n_has_next> or </#if></#list><#if a.or>)</#if></#macro>