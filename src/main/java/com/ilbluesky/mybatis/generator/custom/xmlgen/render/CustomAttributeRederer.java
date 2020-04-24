package com.ilbluesky.mybatis.generator.custom.xmlgen.render;

import org.mybatis.generator.api.dom.xml.Attribute;

public class CustomAttributeRederer {

    public static String render(Attribute attribute) {
        return attribute.getName() + "=\"" + attribute.getValue() + "\"";
    }
}
