<#-- 转译后的左大括号-->
<#assign lp =r"{" >

<#-- 转译后的右大括号-->
<#assign rp =r"}" >

<#-- 表名-->
<#assign tableName =tableModel.getTableName() >

<#-- 实体类所在包的包名-->
<#assign entityPackage =basePackage+".entity" >

<#-- Dao类所在包的包名-->
<#assign daoPackage =basePackage+".dao" >

<#-- Controller类所在包的包名-->
<#assign controllerPackage =basePackage+".controller" >

<#-- 实体类的类名-->
<#assign entityName =tableModel.entityClassName >

<#assign entityNameFirstLower = entityName?uncap_first >

<#-- 实体类的全限定名-->
<#assign entityFullName =entityPackage+"."+entityName >

<#-- Dao类的类名-->
<#assign daoName = entityName+"Dao" >

<#assign daoNameFirstLower = daoName?uncap_first >

<#-- Dao类的全限定名-->
<#assign daoFullName =daoPackage+"."+daoName >

<#-- Controller类的类名-->
<#assign controllerName = entityName+"Controller" >
