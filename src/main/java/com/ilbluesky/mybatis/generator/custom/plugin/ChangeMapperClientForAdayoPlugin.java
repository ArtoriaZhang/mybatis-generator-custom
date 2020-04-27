package com.ilbluesky.mybatis.generator.custom.plugin;

import com.ilbluesky.mybatis.generator.custom.codegen.elements.AbstractJavaModelPluginAdaptor;
import com.ilbluesky.mybatis.generator.custom.codegen.elements.AbstractMapperPluginAdaptor;
import com.ilbluesky.mybatis.generator.custom.codegen.elements.ModelExampleClassModifier;
import com.ilbluesky.mybatis.generator.custom.codegen.elements.SelectByExampleByPageAdder;
import java.util.List;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ChangeMapperClientForAdayoPlugin extends PluginAdapter {

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


    /**
     * java mapper 生成后，此方法会被执行
     * @param interfaze
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze,
            IntrospectedTable introspectedTable) {

        AbstractMapperPluginAdaptor adder = new SelectByExampleByPageAdder(interfaze,
                introspectedTable, this);
        adder.execute();

        return super.clientGenerated(interfaze, introspectedTable);
    }


    /**
     * 改变由MBG生成的java example class
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable) {

        AbstractJavaModelPluginAdaptor modifier = new ModelExampleClassModifier(topLevelClass,
                introspectedTable, this);

        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }
}
