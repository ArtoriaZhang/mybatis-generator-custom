package com.ilbluesky.mybatis.generator.custom.xmlgen;

import com.ilbluesky.mybatis.generator.custom.xmlgen.render.CustomDocumentRenderer;
import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.config.Context;

public class CustomXmlFormatter implements XmlFormatter {

    protected Context context;

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public String getFormattedContent(Document document) {
        return new CustomDocumentRenderer().render(document);
    }
}
