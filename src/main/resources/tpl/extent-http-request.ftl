<#ftl output_format="HTML">
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpRequestAttachment" -->
<div id="method"><#if data.method??>${data.method}<#else>GET</#if></div> 
<div id="endpoint"><#if data.url??>${data.url}<#else>Unknown</#if></div>

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

<#if (data.requestParameters)?has_content>
<h4>Request Parameters</h4>
<table id="requestParams">
    <#list data.requestParameters as name, value>
        <tr><td>${name}</td><td>${value}</td></tr>
    </#list>
</table>
</#if>

<#if (data.queryParameters)?has_content>
<h4>Query Parameters</h4>
<table id="queryParams">
    <#list data.queryParameters as name, value>
        <tr><td>${name}</td><td>${value}</td></tr>
    </#list>
</table>
</#if>

<#if (data.formParameters)?has_content>
<h4>Form Parameters</h4>
<table id="formParams">
    <#list data.formParameters as name, value>
        <tr><td>${name}</td><td>${value}</td></tr>
    </#list>
</table>
</#if>

<#if (data.pathParameters)?has_content>
<h4>Path Parameters</h4>
<table id="pathParams">
    <#list data.pathParameters as name, value>
        <tr><td>${name}</td><td>${value}</td></tr>
    </#list>
</table>
</#if>

<#if (data.multiParts)?has_content>
<h4>Multi Part</h4>
<table id="parts">
    <#list data.multiParts as part>
        <tr><td>${part['name']}</td><td>${part['content']}</td><td>${part['mime']}</td><td>${part['file']}</td></tr>
    </#list>
</table>
</#if>