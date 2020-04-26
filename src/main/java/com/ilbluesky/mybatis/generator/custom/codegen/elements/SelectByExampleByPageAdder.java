package com.ilbluesky.mybatis.generator.custom.codegen.elements;

import com.ilbluesky.mybatis.generator.custom.plugin.ChangeMapperClientForAdayoPlugin;
import java.util.Set;
import java.util.TreeSet;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;

public class SelectByExampleByPageAdder extends AbstractMapperPluginAdaptor {

    public SelectByExampleByPageAdder(Interface interfaze,
            IntrospectedTable introspectedTable,
            ChangeMapperClientForAdayoPlugin changeMapperClientForAdayoPlugin) {

        super(interfaze, introspectedTable, changeMapperClientForAdayoPlugin);
    }

    @Override
    public void execute() {

        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        importedTypes.add(returnType);

        Method method = new Method(introspectedTable.getSelectByExampleStatementId()
                + "ByPage");

        returnType.addTypeArgument(
                new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        method.setReturnType(returnType);

        method.addParameter(new Parameter(
                new FullyQualifiedJavaType(introspectedTable.getExampleType()), "example"));

        method.setAbstract(true);

        interfaze.addMethod(method);
    }
}
