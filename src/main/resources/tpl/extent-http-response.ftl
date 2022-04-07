<#ftl output_format="HTML">
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpResponseAttachment" -->
<div id="code"><#if data.responseCode??>${data.responseCode} <#else>Unknown</#if></div>
<#if data.url??><div id="url">${data.url}</div></#if>

<#if data.body??>
<h4>Body</h4>
<div>
    <pre id="body" class="preformated-text">
    ${data.body}
    </pre>
</div>
</#if>

<#if (data.headers)?has_content>
<h4>Headers</h4>
<table id="headers">
    <#list data.headers as name, value>
        <tr><td>${name}</td><td>${value}</td></tr>
    </#list>
</table>
</#if>


<#if (data.cookies)?has_content>
<h4>Cookies</h4>
<table id="cookies">
    <#list data.cookies as name, value>
        <tr><td>${name}</td><td>${value}</td></tr>
    </#list>
</table>
</#if>