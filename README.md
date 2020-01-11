# mybatis-generator-custom
MBG custom plugin.

This plugin can generate entity with swagger annotation which value comes from oracle comment. 

Usage:

(Notice: This project need the the dependency of [Mybatis Generator](https://github.com/ArtoriaZhang/generator) in your local maven repository)

1. Add the following config snippet to your own MBG generator.xml

~~~
<generatorConfiguration>
	...
	<context ...>
        ...
		    <plugin type="com.ilbluesky.mybatis.generator.custom.plugin.SwaggerAnnotationForOraclePlugin" >
		    	<property name="COLUMN_COMMENT_TABLE_NAME" value="ALL_COL_COMMENTS" />
		    	<property name="TABLE_COMMENT_TABLE_NAME" value="ALL_TAB_COMMENTS" />
    		</plugin>
    	...
    </context>
    ...
</generatorConfiguration>
~~~

1. Run App main method or run the jar packaged with dependencies using 'java -jar ...'. Be aware that pass '-configfile'
parameter to runtime env.  