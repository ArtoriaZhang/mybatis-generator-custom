package com.ilbluesky.mybatis.generator.custom.xmlgen.render;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.mybatis.generator.api.dom.xml.ElementVisitor;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.VisitableElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.CustomCollectors;

public class CustomElementRenderer implements ElementVisitor<Stream<String>> {

    @Override
    public Stream<String> visit(TextElement element) {
        return Stream.of(element.getContent());
    }

    @Override
    public Stream<String> visit(XmlElement element) {
        if (element.hasChildren()) {
            return renderWithChildren(element);
        } else {
            return renderWithoutChildren(element);
        }
    }

    private Stream<String> renderWithChildren(XmlElement element) {
        return Stream.of(renderOpen(element), renderChildren(element), renderClose(element))
                .flatMap(s -> s);
    }

    private String renderAttributes(XmlElement element) {
        return element.getAttributes().stream()
                .map(CustomAttributeRederer::render)
                .collect(CustomCollectors.joining(" ", " ", ""));
    }

    private Stream<String> renderWithoutChildren(XmlElement element) {
        return Stream.of("<"
                + element.getName()
                + this.renderAttributes(element)
                + " />");
    }

    private Stream<String> renderOpen(XmlElement element) {
        return Stream.of("<" //$NON-NLS-1$
                + element.getName()
                + renderAttributes(element)
                + " >"); //$NON-NLS-1$
    }

    private Stream<String> renderChildren(XmlElement element) {
        return element.getElements().stream()
                .flatMap(this::renderChild)
                .map(this::indent);
    }

    private Stream<String> renderChild(VisitableElement child) {
        return child.accept(this);
    }

    private String indent(String s) {
        return "  " + s; //$NON-NLS-1$
    }

    private Stream<String> renderClose(XmlElement element) {
        return Stream.of("</" //$NON-NLS-1$
                + element.getName()
                + ">"); //$NON-NLS-1$
    }
}
