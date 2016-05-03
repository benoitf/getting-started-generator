package com.codenvy.factory.generator;

import java.util.List;

/**
 * Wrapper for mustache to provide the items
 * @author Florent Benoit
 */
public class MustacheContext {

    private final List<CodenvyYamlDesc> codenvyDescs;

    public MustacheContext(List<CodenvyYamlDesc> codenvyDescs){
        this.codenvyDescs = codenvyDescs;
    }

    List<CodenvyYamlDesc> items() {
        return this.codenvyDescs;
    }

}