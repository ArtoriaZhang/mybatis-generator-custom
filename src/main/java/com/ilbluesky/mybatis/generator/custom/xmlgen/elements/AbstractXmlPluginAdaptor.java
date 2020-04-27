package com.ilbluesky.mybatis.generator.custom.xmlgen.elements;

import com.ilbluesky.mybatis.generator.custom.api.gen.AbstractPluginAdaptor;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.XmlElement;

public abstract class AbstractXmlPluginAdaptor extends AbstractPluginAdaptor {

    protected XmlElement answer;
    protected boolean isSimple = false;

    public AbstractXmlPluginAdaptor(XmlElement toBeOverrideEle,
            IntrospectedTable introspectedTable,
            PluginAdapter adapter) {

        super(introspectedTable, adapter);
        this.answer = toBeOverrideEle;
    }

    public abstract void override();

    protected void clearElement() {
        this.answer.getAttributes().clear();
        this.answer.getElements().clear();
    }

    @Override
    public void execute() {

    }
}
