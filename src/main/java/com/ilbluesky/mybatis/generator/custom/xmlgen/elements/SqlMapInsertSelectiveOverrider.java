package com.ilbluesky.mybatis.generator.custom.xmlgen.elements;

import com.ilbluesky.mybatis.generator.custom.plugin.ChangeGeneratedXmlForAdayoSrmPlugin;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

public class SqlMapInsertSelectiveOverrider extends AbstractXmlPluginAdaptor {

    public SqlMapInsertSelectiveOverrider(XmlElement answer,
            IntrospectedTable introspectedTable,
            ChangeGeneratedXmlForAdayoSrmPlugin changeGeneratedXmlForAdayoSrmPlugin) {
        super(answer, introspectedTable, changeGeneratedXmlForAdayoSrmPlugin);
    }

    @Override
    public void override() {
        this.clearElement();

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getInsertSelectiveStatementId())); //$NON-NLS-1$

        FullyQualifiedJavaType parameterType = introspectedTable.getRules()
                .calculateAllFieldsClass();

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);

        // 加入自动ID生成器
        answer.addElement(new TextElement("<bind name=\"_uuid\" "
                + "value=\"@com.els.base.utils.uuid.UUIDGenerator@generateUUIDAndSetId(#this)\"/>"));

/*        GeneratedKey gk = introspectedTable.getGeneratedKey();
        if (gk != null) {
            introspectedTable.getColumn(gk.getColumn()).ifPresent(introspectedColumn -> {
                // if the column is null, then it's a configuration error. The
                // warning has already been reported
                if (gk.isJdbcStandard()) {
                    answer.addAttribute(new Attribute("useGeneratedKeys", "true")); //$NON-NLS-1$ //$NON-NLS-2$
                    answer.addAttribute(
                            new Attribute("keyProperty", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
                    answer.addAttribute(
                            new Attribute("keyColumn", introspectedColumn.getActualColumnName())); //$NON-NLS-1$
                } else {
                    answer.addElement(getSelectKey(introspectedColumn, gk));
                }
            });
        }*/

        StringBuilder sb = new StringBuilder();

        sb.append("insert into "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement insertTrimElement = new XmlElement("trim"); //$NON-NLS-1$
        insertTrimElement.addAttribute(new Attribute("prefix", "(")); //$NON-NLS-1$ //$NON-NLS-2$
        insertTrimElement.addAttribute(new Attribute("suffix", ")")); //$NON-NLS-1$ //$NON-NLS-2$
        insertTrimElement.addAttribute(new Attribute("suffixOverrides", ",")); //$NON-NLS-1$ //$NON-NLS-2$
        answer.addElement(insertTrimElement);

        XmlElement valuesTrimElement = new XmlElement("trim"); //$NON-NLS-1$
        valuesTrimElement.addAttribute(new Attribute("prefix", "values (")); //$NON-NLS-1$ //$NON-NLS-2$
        valuesTrimElement.addAttribute(new Attribute("suffix", ")")); //$NON-NLS-1$ //$NON-NLS-2$
        valuesTrimElement.addAttribute(new Attribute("suffixOverrides", ",")); //$NON-NLS-1$ //$NON-NLS-2$
        answer.addElement(valuesTrimElement);

        for (IntrospectedColumn introspectedColumn :
                ListUtilities.removeIdentityAndGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {

            if (introspectedColumn.isSequenceColumn()
                    || introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                // if it is a sequence column, it is not optional
                // This is required for MyBatis3 because MyBatis3 parses
                // and calculates the SQL before executing the selectKey

                // if it is primitive, we cannot do a null check
                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities
                        .getEscapedColumnName(introspectedColumn));
                sb.append(',');
                insertTrimElement.addElement(new TextElement(sb.toString()));

                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities
                        .getParameterClause(introspectedColumn));
                sb.append(',');
                valuesTrimElement.addElement(new TextElement(sb.toString()));

                continue;
            }

            if ("id".equalsIgnoreCase(introspectedColumn.getActualColumnName())) {
                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
                sb.append(',');
                insertTrimElement.addElement(new TextElement(sb.toString()));

                valuesTrimElement.addElement(new TextElement("#{_uuid},"));
            } else {
                sb.setLength(0);
                sb.append(introspectedColumn.getJavaProperty());
                sb.append(" != null"); //$NON-NLS-1$
                XmlElement insertNotNullElement = new XmlElement("if"); //$NON-NLS-1$
                insertNotNullElement.addAttribute(new Attribute(
                        "test", sb.toString())); //$NON-NLS-1$

                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities
                        .getEscapedColumnName(introspectedColumn));
                sb.append(',');
                insertNotNullElement.addElement(new TextElement(sb.toString()));
                insertTrimElement.addElement(insertNotNullElement);

                sb.setLength(0);
                sb.append(introspectedColumn.getJavaProperty());
                sb.append(" != null"); //$NON-NLS-1$
                XmlElement valuesNotNullElement = new XmlElement("if"); //$NON-NLS-1$
                valuesNotNullElement.addAttribute(new Attribute(
                        "test", sb.toString())); //$NON-NLS-1$

                sb.setLength(0);
                sb.append(MyBatis3FormattingUtilities
                        .getParameterClause(introspectedColumn));
                sb.append(',');
                valuesNotNullElement.addElement(new TextElement(sb.toString()));
                valuesTrimElement.addElement(valuesNotNullElement);
            }
        }
    }
}
