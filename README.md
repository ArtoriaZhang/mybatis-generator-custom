# mybatis-generator-custom
MBG custom plugin.

Include some custom plugins and client generators.
 
Usage:

(Notice: This project need the dependency of [Mybatis Generator](https://github.com/ArtoriaZhang/generator). You can install it in your local maven repository or add to your workplace as a dependence module.)

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


## Concepts & Architecture

1. ConfigurationParser.parseConfiguration(configFile) load properties & plugin & context. This will also determine bunch of GENERATORS which to be use.;
2. Looping in CONTEXT_TO_BE_RUN, handing each CONTEXT. Generating introspect tables, then generate the POJO of java client / mapper. Building up XML file.
