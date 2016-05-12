package com.codenvy.factory.generator;

import java.util.List;

/**
 * Wrapper for mustache to provide the items
 * @author Florent Benoit
 */
public class MustacheContext {

    private final List<CodenvyDesc> codenvyDescs;

    public MustacheContext(List<CodenvyDesc> codenvyDescs){
        this.codenvyDescs = codenvyDescs;
    }

    List<CodenvyDesc> items() {
        return this.codenvyDescs;
    }

}