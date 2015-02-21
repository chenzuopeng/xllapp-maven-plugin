<#assign columns =tableModel.getIncludedColumns() >
package ${entityPackage};

import org.xllapp.mvc.entity.IdEntity;
<#list tableModel.getIncludedColumnTypes() as columnType>
<#if ! columnType?contains("java.lang")>
import ${columnType};
</#if>
</#list>

public class ${entityName} extends IdEntity {

<#list columns as column>
<#t><#if column.getJavaFieldName() !="id">
    private ${column.getSimpleJavaType()} ${column.getJavaFieldName()};
    
</#if>
</#list>

<#list columns as column>
<#t><#if column.getJavaFieldName() !="id">
    public ${column.getSimpleJavaType()} get${column.getJavaFieldNameFirstUpper()}() {
        return this.${column.getJavaFieldName()};
    }

    public void set${column.getJavaFieldNameFirstUpper()}(${column.getSimpleJavaType()} ${column.getJavaFieldName()}) {
        this.${column.getJavaFieldName()} = ${column.getJavaFieldName()};
    }
</#if><#t>
</#list>

}