<#assign idColumn =tableModel.getIdColumn() >
<#assign columns =tableModel.getIncludedColumns() >
<#macro formInput column>
       <#local javaFieldName=column.getJavaFieldName()!>
       <#switch column.getInputTypeAsString()>
              <#case "FILE">
              <#case "IMAGE">
              <#case "TEXT">
	              <#t><input class="save_form_field_input<#if column.isNotModified() > save_form_field_readonly</#if>" id="${javaFieldName}" name="${javaFieldName}"  type="text" <#if column.isNotModified() >readonly="true"  onfocus="this.blur();"</#if> value="${r'<#if entity?has_content>${entity.'}${javaFieldName}${r'!}</#if>'}" <#if column.getInputTypeAsString() != "TEXT">fileUploadType="${column.getInputTypeAsString()?lower_case}"</#if>/>
			      <#break>
              <#case "TEXTAREA">
                  <#t><textarea class="save_form_field_textarea<#if column.isNotModified() > save_form_field_readonly</#if>" id="${javaFieldName}" name="${javaFieldName}" <#if column.isNotModified() >readonly="true"  onfocus="this.blur();"</#if>>${r'<#if entity?has_content>${entity.'}${javaFieldName}${r'!}</#if>'}</textarea>
			      <#break>
			  <#case "DATE">
			       <#t><input class="save_form_field_input<#if column.isNotModified() > save_form_field_readonly</#if>" id="${javaFieldName}" name="${javaFieldName}" type="text" readonly="true" <#if !column.isNotModified() >onclick="showDatePicker('${javaFieldName}')"<#else>onfocus="this.blur();"</#if> value="${r'<#if entity?has_content && entity.'}${javaFieldName}${r'?has_content>${entity.'}${javaFieldName}${r'?datetime}</#if>'}"/>
			       <#break>
			  <#case "SELECT">
			       <#lt><select class="search_field_input search_field_select<#if column.isNotModified() > save_form_field_readonly</#if>" id="${javaFieldName}" name="${javaFieldName}" <#if column.isNotModified() >readonly="true"  onfocus="this.blur();"</#if>>
				            <option value="">选择</option>
			        <#if column.getSelectKeys()?has_content>
						<#list column.getSelectKeyArray() as key>
							<option value="${column.getSelectValueArray()[key_index]}" ${r"<#if entity?has_content && entity."}${javaFieldName}${r"!?string == '"}${column.getSelectValueArray()[key_index]}${r"'>selected='selected'</#if>"}>${key}</option>
						</#list>
					</#if>
			       </select><#rt>
       </#switch>
</#macro>
<!DOCTYPE html>
<html>
<head>
<title>表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" media="screen" href="${r'<@full_path path="js/jBox/Skins/Default/jbox.css"/>'}"/>
<link type="text/css" rel="stylesheet" media="screen" href="${r'<@full_path path="css/icity.css"/>'}"/>
<script type="text/javascript" src="${r'<@full_path path="js/jquery/jquery-1.8.0.min.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/jquery/jquery.validate.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/jquery/jquery.validate.messages_zh.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/My97DatePicker/WdatePicker.js"/>'}" ></script>
<script type="text/javascript" src="${r'<@full_path path="js/jBox/jquery.jBox-2.3.min.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/jBox/i18n/jquery.jBox-zh-CN.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/icity.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="js/uploadfile.js"/>'}"></script>
<script type="text/javascript" src="${r'<@full_path path="upload/file/js/variables"/>'}"></script>

<script type="text/javascript">
<!--
$().ready(function() {
     var validator = $("#saveForm").validate({
          rules: {
      <#list columns as column>
          <#if column.validateString?has_content && column.columnName != idColumn.columnName>
				${column.getJavaFieldName()!}: {
					<#list column.getValidateStringArray() as item>
						${item}<#if item_has_next>,</#if>
				    </#list>
				}<#if column_has_next>,</#if>
			</#if>
	   </#list>
		 },
		 errorClass:"search_error",
		 errorElement:"div"});
	  $(".reset").click(function() {
           validator.resetForm();
      });
});
//-->
</script>
</head>

<body>
<form id="saveForm" method="post"  action="?act=SAVE">
<input type="hidden" id="${idColumn.getJavaFieldName()!}" name="${idColumn.getJavaFieldName()!}"  value="${r'<#if entity?has_content>${entity.'}${idColumn.getJavaFieldName()!}${r'}</#if>'}"/>
<table class="save_form_table" border="1">
    <#list columns as column>
	    <#if column.getJavaFieldName() != idColumn.getJavaFieldName() >
	        <tr>
				<td class="save_form_field_label"><span>${column.getLabel()!}</span></td>
				<td><@formInput column/></td>
		    </tr>
	    </#if>
    </#list>
		<tr>
			<td colspan="2" class="save_form_button_panel"><input type="submit"  value="保存"/><input type="reset" class="reset" value="重置"/></td>
		</tr>
</table>
</form>
</body>
</html>