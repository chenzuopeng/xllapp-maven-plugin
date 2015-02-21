<#assign idColumn =tableModel.getIdColumn() >
<#assign entityResultMap =entityNameFirstLower+"ResultMap" >
<#assign columnsSqlId =entityNameFirstLower +"Columns">
<#macro mapperEl value><#t>${r"#{"}${value}${r"}"}</#macro>
<#macro whereFilterValue column javaFieldName>
	 <#if column.isDateTime() >
	     <#lt>str_to_date(<@mapperEl javaFieldName />,'%Y-%m-%d %H:%i:%s')<#rt>
	 <#else>
		 <@mapperEl javaFieldName/>
	 </#if>
</#macro>
<#macro columnValue column><#if column.isSysdateAsValue() >now()<#else><@mapperEl column.getJavaFieldName()/></#if></#macro>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${daoFullName}">

   <resultMap id="${entityResultMap}" type="${entityFullName}">
      <#list tableModel.getIncludedColumns() as column>
          <result property="${column.getJavaFieldName()}" column="${column.getColumnName()}"/>
      </#list>
   </resultMap>
   
   	<sql id="${columnsSqlId}">
	    <![CDATA[
		<#list tableModel.getIncludedColumns() as column>${column.getColumnName()}<#if column_has_next>,</#if></#list>
	    ]]>
	</sql>
	
	<select id="query"  parameterType="map"  resultMap="${entityResultMap}">
		select <include refid="${columnsSqlId}" />
        from ${tableName}
		<where>
		    <#list tableModel.getIncludedColumns() as column>
		       <#if column.isWhereFilter()>
			     <#switch column.getWhereFilterAsString()>
			      <#case  "EQ">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} = <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()/>
			      </if>
			      <#break>
			      <#case  "NE">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} != <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()/>
			      </if>
			      <#break>
			      <#case  "GT">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} <![CDATA[>]]> <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()/>
			      </if>
			      <#break>
			      <#case  "GE">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} <![CDATA[>=]]> <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()/>
			      </if>
			      <#break>
			      <#case  "LT">
			     <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} <![CDATA[<]]> <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()/>
			      </if>
			      <#break>
			      <#case  "LE">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} <![CDATA[<=]]> <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()/>
			      </if>
			      <#break>
			      <#case  "LIKE">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} like concat('%',<@mapperEl column.getJavaFieldName()/>,'%')
			      </if>
			      <#break>
			      <#case  "NOT_LIKE">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} not like concat('%',<@mapperEl column.getJavaFieldName()/>,'%')
			      </if>
			      <#break>
			      <#case  "BETWEEN">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()+"Begin"}) and @Ognl@isNotEmpty(${column.getJavaFieldName()+"End"})">
				  and ${column.getColumnName()} between <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()+"Begin"/> and <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()+"End"/>
			      </if>
			      <#break>
			      <#case  "NOT_BETWEEN">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()+"Begin"}) and @Ognl@isNotEmpty(${column.getJavaFieldName()+"End"})">
				  and ${column.getColumnName()} not between <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()+"Begin"/> and <@whereFilterValue column=column javaFieldName=column.getJavaFieldName()+"End"/>
			      </if>
			      <#break>
			      <#case  "IN">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} in
				  <foreach item="item" collection="${column.getJavaFieldName()+"_array"}" separator="," open="(" close=")" index="index"><@whereFilterValue column=column javaFieldName="item"/></foreach>
			      </if>
			      <#break>
			      <#case  "NOT_IN">
			      <if test="@Ognl@isNotEmpty(${column.getJavaFieldName()})">
				  and ${column.getColumnName()} not in
				  <foreach item="item" collection="${column.getJavaFieldName()+"_array"}" separator="," open="(" close=")" index="index"><@whereFilterValue column=column javaFieldName="item"/></foreach>
			      </if>
			      </#switch>
			   </#if>
			</#list>
		</where>
		order by ${idColumn.getColumnName()} desc
	</select>

	<select id="get" parameterType="long" resultMap="${entityResultMap}">		
		select <include refid="${columnsSqlId}" />
	    <![CDATA[
		    from ${tableName} where ${idColumn.getColumnName()} = <@mapperEl idColumn.getJavaFieldName()/> 
	    ]]>
	</select>
	
	<insert id="insert" parameterType="${entityFullName}" useGeneratedKeys="true" keyProperty="${idColumn.getJavaFieldName()}">        
    <![CDATA[
        insert into  ${tableName} (
        <#list  tableModel.getNotIdIncludedColumns() as column>
        	${column.getColumnName()} <#if column_has_next>,</#if>
        </#list>
        ) values (
       <#list  tableModel.getNotIdIncludedColumns() as column>
        	<@columnValue column/><#if column_has_next>,</#if>
        </#list>        
        )
    ]]>  
	</insert>

	<update id="update" parameterType="${entityFullName}">
	<![CDATA[
		update ${tableName} set 
		<#list  tableModel.getNotIdIncludedColumns() as column>
	       ${column.getColumnName()} = <@mapperEl column.getJavaFieldName()/><#if column_has_next>,</#if>
		 </#list>
		where  ${idColumn.getColumnName()} = <@mapperEl idColumn.getJavaFieldName()/>
	 ]]>
	</update>
	
	<delete id="delete" parameterType="int">
		delete from ${tableName} where ${idColumn.getColumnName()} = <@mapperEl idColumn.getJavaFieldName()/>
	</delete>
	
	<delete id="deletes">
		delete from ${tableName} where ${idColumn.getColumnName()} in 
        <foreach item="item" index="index" collection="array" open="(" separator="," close=")"><@mapperEl "item" /></foreach>
    </delete>
	
</mapper> 
