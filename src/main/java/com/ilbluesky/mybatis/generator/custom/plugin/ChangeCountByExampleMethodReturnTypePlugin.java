package com.ilbluesky.mybatis.generator.custom.plugin;

import java.util.List;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;

public class ChangeCountByExampleMethodReturnTypePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     *  change the default return type of count by example from <code>long</code> to <code>int</code>
     * @param method
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientCountByExampleMethodGenerated(Method method, Interface interfaze,
            IntrospectedTable introspectedTable) {
        method.setReturnType(new FullyQualifiedJavaType("int"));
        return super.clientCountByExampleMethodGenerated(method, interfaze, introspectedTable);
    }
}
