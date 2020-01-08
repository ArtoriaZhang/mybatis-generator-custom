# mybatis-generator-custom
MBG custom plugin

usage:

1.add plugin to context node

<generatorConfiguration>
	...
	<context ...>
		    <plugin type="com.ilbluesky.mybatis.generator.custom.plugin.SwaggerAnnotationForOraclePlugin" >
		    	<property name="COLUMN_COMMENT_TABLE_NAME" value="ALL_COL_COMMENTS" />
		    	<property name="TABLE_COMMENT_TABLE_NAME" value="ALL_TAB_COMMENTS" />
    		</plugin>
    	...
    </context>
    ...
</generatorConfiguration>

2. run App.java