package com.ilbluesky.mybatis.generator.custom.codegen;

import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;

/**
 * custom java mapper interface generator
 */
public class CustomJavaMapperGenerator extends JavaMapperGenerator {

    /**
     * Need to specify the PROJECT manually.
     */
    public CustomJavaMapperGenerator() {
        this("MAVEN");
    }

    public CustomJavaMapperGenerator(String project) {
        super(project);
    }
}
