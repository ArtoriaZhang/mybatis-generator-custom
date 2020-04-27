package com.ilbluesky.mybatis.generator.custom.api.gen;

import java.util.Properties;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.Context;

public abstract class AbstractPluginAdaptor {

    protected IntrospectedTable introspectedTable;
    protected Context context;
    protected Properties properties;

    public AbstractPluginAdaptor () {}

    public AbstractPluginAdaptor(IntrospectedTable introspectedTable, PluginAdapter adapter) {
        this. introspectedTable = introspectedTable;
        this.context = adapter.getContext();
        this.properties = adapter.getProperties();
    }

    public abstract void execute();
}
