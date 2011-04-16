<#--
    Include this file into your scala templates.

-->/* Preprocessed file - Do not edit */

<#macro Tuple n>Tuple${n}[<#list 1..n as i>T${i}<#if i != n>, </#if></#list>]</#macro>
<#macro Product n>Product${n}[<#list 1..n as i>T${i}<#if i != n>, </#if></#list>]</#macro>
<#macro tlist n><#list 1..n as i>T${i}<#nested/><#if i != n>, </#if></#list></#macro>
<#macro tbounds n bounds><@tlist n=n> : ${bounds}</@tlist></#macro>

