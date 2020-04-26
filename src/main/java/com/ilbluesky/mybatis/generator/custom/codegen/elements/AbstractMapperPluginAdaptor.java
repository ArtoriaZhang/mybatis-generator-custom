package com.ilbluesky.mybatis.generator.custom.codegen.elements;

import com.ilbluesky.mybatis.generator.custom.api.gen.AbstractPluginAdaptor;
import com.ilbluesky.mybatis.generator.custom.plugin.ChangeMapperClientForAdayoPlugin;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;

public abstract class AbstractMapperPluginAdaptor extends AbstractPluginAdaptor {

    protected Interface interfaze;

    public AbstractMapperPluginAdaptor(IntrospectedTable introspectedTable,
            PluginAdapter adapter) {
        super(introspectedTable, adapter);
    }

    public AbstractMapperPluginAdaptor(Interface interfaze,
            IntrospectedTable introspectedTable,
            ChangeMapperClientForAdayoPlugin changeMapperClientForAdayoPlugin) {

        this(introspectedTable, changeMapperClientForAdayoPlugin);
        this.interfaze = interfaze;
    }

    public abstract void execute();
}
