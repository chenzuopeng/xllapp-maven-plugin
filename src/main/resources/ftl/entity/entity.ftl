<#assign columns =tableModel.getIncludedColumns() >
package ${basePackage+".entity"};

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
<#list tableModel.getIncludedColumnTypes() as columnType>
<#if ! columnType?contains("java.lang")>
import ${columnType};
</#if>
</#list>

public class ${tableModel.entityClassName} implements Serializable {
	
<#list columns as column>
    private ${column.getSimpleJavaType()} ${column.getJavaFieldName()};
    
</#list>

<#list columns as column>
    public ${column.getSimpleJavaType()} get${column.getJavaFieldNameFirstUpper()}() {
        return this.${column.getJavaFieldName()};
    }

    public void set${column.getJavaFieldNameFirstUpper()}(${column.getSimpleJavaType()} ${column.getJavaFieldName()}) {
        this.${column.getJavaFieldName()} = ${column.getJavaFieldName()};
    }
</#list>

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
}