<#assign columns =tableModel.getIncludedColumns() >
<#macro textInput column>
	<#switch column.getWhereFilterAsString()>
		<#case  "BETWEEN">
		<#case  "NOT_BETWEEN">
		   <#local beginName=column.getJavaFieldName() +"Begin">
		   <#local endName=column.getJavaFieldName() +"End">
		   <input class="search_field_input" id="${beginName}" name="${beginName}" type="text" value="${r"${RequestParameters['"}${beginName}${r"']!}"}" />至<input class="search_field_input" id="${endName}" name="${endName}" type="text" value="${r"${RequestParameters['"}${endName}${r"']!}"}"/>
		<#break>
		<#default>
		   <input class="search_field_input" id="${column.getJavaFieldName()}" name="${column.getJavaFieldName()}" type="text"  value="${r"${RequestParameters['"}${column.getJavaFieldName()}${r"']!}"}"/>
	</#switch>
</#macro>
<#macro dateInput column>
	<#switch column.getWhereFilterAsString()>
		<#case  "BETWEEN">
		<#case  "NOT_BETWEEN">
		   <#local beginName=column.getJavaFieldName() +"Begin">
		   <#local endName=column.getJavaFieldName() +"End">
		   <input class="search_field_input" id="${beginName}" name="${beginName}" type="text" readonly="readonly" onclick="showDatePicker('${beginName}')" value="${r"${RequestParameters['"}${beginName}${r"']!}"}"/>至<input class="search_field_input" id="${endName}" name="${endName}" type="text" readonly="readonly" onclick="showDatePicker('${endName}')" value="${r"${RequestParameters['"}${endName}${r"']!}"}"/>
		<#break>
		<#default>
		   <input class="search_field_input" id="${column.getJavaFieldName()}" name="${column.getJavaFieldName()}" type="text" readonly="readonly" onclick="showDatePicker('${column.getJavaFieldName()}')" value="${r"${RequestParameters['"}${column.getJavaFieldName()}${r"']!}"}"/>
	</#switch>
</#macro>
<#macro selectInput column>
    <#switch column.getWhereFilterAsString()>
		<#case  "BETWEEN">
		<#case  "NOT_BETWEEN">
		    <#local beginName=column.getJavaFieldName() +"Begin">
		    <#local endName=column.getJavaFieldName() +"End">
		    <select class="search_field_input search_field_select" id="${beginName}" name="${beginName}">
					<@selectOptions column=column javaFieldName=beginName/>
			</select>
			至
			<select class="search_field_input search_field_select" id="${endName}" name="${endName}">
				    <@selectOptions column=column javaFieldName=endName/>
			</select>
		<#break>
		<#default>
		    <select class="search_field_input search_field_select" id="${column.getJavaFieldName()}" name="${column.getJavaFieldName()}">
				    <@selectOptions column=column javaFieldName=column.getJavaFieldName()/>
			</select>
	</#switch>
</#macro>
<#macro selectOptions column javaFieldName>
                <option value="">选择</option>
        <#if column.getSelectKeys()?has_content>
			<#list column.getSelectKeyArray() as key>
				<option value="${column.getSelectValueArray()[key_index]}" ${r"<#if RequestParameters['"}${javaFieldName}${r"']! == '"}${column.getSelectValueArray()[key_index]}${r"'>selected='selected'</#if>"}>${key}</option>
			</#list>
		</#if>
</#macro>
<#-- 用于生成获取实体字段值的表达式  -->
<#macro javaFieldValue column>
       <#if column.isEnumColumn()>
             <#t>${r'${'}${column.getJavaFieldName()}Map[item.${column.getJavaFieldName()}?string]${r'}'}
       <#else>
             <#t>${r'${item.'}${column.getJavaFieldName()}<#if column.isDateTime() >${r'?datetime'}</#if>${r'}'}
       </#if>
</#macro>
<#-- 生成枚举列的value与key的Map  -->
<#list columns as column>
        <#if column.isEnumColumn()>
            <#lt>${r'<#assign '}${column.getJavaFieldName()}Map${r' = {'}<#list column.getSelectKeyArray() as key>"${column.getSelectValueArray()[key_index]}":"${key}"<#if key_has_next>,</#if></#list>${r'}>'}
		</#if>
</#list>
<!DOCTYPE html>
<html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" media="screen" href="${r'<@full_path path="css/icity.css"/>'}"/>
<script type="text/javascript" src="${r'<@full_path path="js/jquery/jquery-1.8.0.min.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/jquery/jquery.validate.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/jquery/jquery.validate.messages_zh.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/My97DatePicker/WdatePicker.js"/>'}" ></script>
<script type="text/javascript" src="${r'<@full_path path="js/icity.js"/>'}"></script>
<script type="text/javascript">
<!--
$().ready(function() {
		 renderTable();
});
//-->
</script>
</head>

<body>

<#if tableModel.hasWhereFilter() >
<!-- 查询表单 -->
<form id="searchForm" action="?act=QUERY" method="post">
	<fieldset class="search_panel">
		<legend>查询条件</legend>
   <#list columns as column>
         <#if column.isWhereFilter()>
            <table class="search_field">
				 <tr>
					  <td><span>${column.getLabel()!}</span></td>
					  <td>
              <#switch column.getInputTypeAsString()>
			      <#case "SELECT">
							<@selectInput column/>
				  <#break>
			      <#case "DATE">
				            <@dateInput column/>
				  <#break>
				  <#default>
				            <@textInput column/>
			</#switch>
				      </td>
		         </tr>
			</table>
		    </#if>
		</#list>
			<table class="search_button_panel">
				 <tr>
					  <td><input class="search_button" type="submit" value="查询"><input class="search_button" type="button" value="清空" onclick="cleanForm('#searchForm')"></td>
				 </tr>
			</table>
	</fieldset>
</form>
</#if>

<!-- 按钮栏 -->
<table class="button_panel">
	<tr>
		<td><input class="action_button"  type="button" value="添加" onclick="showUrlByButton('?act=SHOW_FORM')"></td>
	</tr>
</table>

<!-- 表格 -->
<table class="data_panel">
	<thead>
		<tr>
		<#list columns as column>
		    <#if column.isHtmlTableRow() >
			<th><span>${column.getLabel()!}</span></th>
			</#if>
		</#list>
		     <th style="width: 100px;"><span>操作</span></th>
		</tr>
	</thead>
	<tbody>
	    ${r'<#list page.result as item>'}
		<tr>
		    <#list columns as column>
	           <#if column.isHtmlTableRow() >
			     <td>${r'<#if item.'}${column.getJavaFieldName()}${r'?has_content>'}<@javaFieldValue column/>${r'</#if>'}</td>
		       </#if>
		    </#list>
			     <td><a href="?act=SHOW_FORM&id=${r'${item.id!}'}">更新</a> | <a href="?act=DELETE&ids=${r'${item.id!}'}">删除</a></td>
		</tr>
		${r'</#list>'}
	</tbody>
</table>

<!-- 分页栏 -->
<table class="pager_panel">
	<tr>
		<td>${r'<@page_navigator/>'}</td>
	</tr>
</table>
</body>
</html>