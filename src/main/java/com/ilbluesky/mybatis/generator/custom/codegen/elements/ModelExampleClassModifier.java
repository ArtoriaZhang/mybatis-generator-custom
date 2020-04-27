package com.ilbluesky.mybatis.generator.custom.codegen.elements;

import com.ilbluesky.mybatis.generator.custom.plugin.ChangeMapperClientForAdayoPlugin;
import java.util.Set;
import java.util.TreeSet;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.AbstractJavaType;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class ModelExampleClassModifier extends AbstractJavaModelPluginAdaptor {

    private static final String SUPER_CLASS_STR = "com.els.base.core.entity.AbstractExample";
    private static final String PAGE_CLASS_STR = "com.els.base.core.entity.PageView";
    private static final String SERIALIZABLE_CLASS_STR = "java.io.Serializable";

    public ModelExampleClassModifier(TopLevelClass topLevelClass,
            IntrospectedTable introspectedTable,
            ChangeMapperClientForAdayoPlugin changeMapperClientForAdayoPlugin) {

        super(topLevelClass, introspectedTable, changeMapperClientForAdayoPlugin);
    }

    @Override
    public void execute() {
        // add imports
        Set<FullyQualifiedJavaType> imports = new TreeSet<>();
        imports.add(new FullyQualifiedJavaType(SERIALIZABLE_CLASS_STR));
        imports.add(new FullyQualifiedJavaType(PAGE_CLASS_STR));
        imports.add(new FullyQualifiedJavaType(SUPER_CLASS_STR));
        topLevelClass.addImportedTypes(imports);

        // add super class
        FullyQualifiedJavaType superClass = new FullyQualifiedJavaType(SUPER_CLASS_STR);
        FullyQualifiedJavaType baseRecordType = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());
        superClass.addTypeArgument(baseRecordType);
        topLevelClass.setSuperClass(superClass);

        // add interface
        this.addSerialInterface(topLevelClass);

        // add pageView field
        FullyQualifiedJavaType pageViewType = new FullyQualifiedJavaType(PAGE_CLASS_STR);
        pageViewType.addTypeArgument(baseRecordType);
        Field field = new Field("pageView", pageViewType);
        field.setVisibility(JavaVisibility.PROTECTED);
        field.setInitializationString("new " + pageViewType.getShortName() + "(1, 10)");
        topLevelClass.addField(field);

        // add serial UID
        this.addSerialIdField(topLevelClass);

        // add getPageView method
        Method method = new Method("getPageView");
        method.addAnnotation("@Override");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(pageViewType);
        method.addBodyLine("return pageView;");
        topLevelClass.addMethod(method);

        // add setPageView method
        method = new Method("setPageView");
        method.addAnnotation("@Override");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(pageViewType, "pageView"));
        method.addBodyLine("this.pageView = pageView;");
        topLevelClass.addMethod(method);

        // add serial interface to GeneratedCriteria
        InnerClass generatedCriteria = topLevelClass.getInnerClasses().get(0);
        this.addSerialInterface(generatedCriteria);
        // add serial id to GeneratedCriteria
        this.addSerialIdField(generatedCriteria);

        // add serial interface to criteria
        InnerClass criteria = topLevelClass.getInnerClasses().get(1);
        this.addSerialInterface(criteria);
        // add serial id to criteria
        this.addSerialIdField(criteria);

        InnerClass criterion = topLevelClass.getInnerClasses().get(2);
        this.addSerialInterface(criterion);
        this.addSerialIdField(criterion);
    }

    private void addSerialIdField(AbstractJavaType type) {
        Field field = new Field("serialVersionUID", new FullyQualifiedJavaType("long"));
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setStatic(true);
        field.setFinal(true);
        field.setInitializationString("1L");
        type.addField(field);
    }

    private void addSerialInterface(AbstractJavaType type) {
        FullyQualifiedJavaType interfaceClass = new FullyQualifiedJavaType(SERIALIZABLE_CLASS_STR);
        type.addSuperInterface(interfaceClass);
    }
}
