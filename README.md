<p><B>maven插件</B></p>

<p>提供代码生成和maven本地缓存清理等功能。</p>

<p><B>插件安装:</B></p>

<p>setting.xml文件中添加插件源: http://61.154.164.33:8907/nexus/content/repositories/releases/</p>

<p>pom.xml文件中添加如下内容:</p>


<pre>
&lt;plugin&gt;<br>
   &lt;groupId&gt;org.xllapp&lt;/groupId&gt;<br>
   &lt;artifactId&gt;xllapp-maven-plugin&lt;/artifactId&gt;<br>
   &lt;version&gt;2.1.0-beta&lt;/version&gt;<br>
&lt;/plugin&gt;<br >
</pre>

<p><B>插件使用</B></p>

<p>启动代码生成器执行: xllapp:mvc-code-generate</p>

<p>清理maven本地缓存执行: xllapp:lufclean</p>  

