package com.ilbluesky.mybatis.generator.custom.codegen.elements;

import com.ilbluesky.mybatis.generator.custom.api.gen.AbstractPluginAdaptor;
import com.ilbluesky.mybatis.generator.custom.plugin.ChangeMapperClientForAdayoPlugin;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public abstract class AbstractJavaModelPluginAdaptor extends AbstractPluginAdaptor {

    TopLevelClass topLevelClass;

    public AbstractJavaModelPluginAdaptor(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable,
            ChangeMapperClientForAdayoPlugin changeMapperClientForAdayoPlugin) {

        super(introspectedTable, changeMapperClientForAdayoPlugin);
        this.topLevelClass = topLevelClass;
    }

}
