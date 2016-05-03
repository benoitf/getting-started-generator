package com.codenvy.factory.generator;

import org.yaml.snakeyaml.Yaml;

import java.io.Reader;

/**
 * @author Florent Benoit
 */
public class CodenvyYamlParser {

    private final Yaml yaml;

    public CodenvyYamlParser() {
        this.yaml = new Yaml();
    }

    public CodenvyDesc analyze(Reader reader) {
        return yaml.loadAs(reader, CodenvyDesc.class);
    }

    public CodenvyDesc analyze(String content) {
        return yaml.loadAs(content, CodenvyDesc.class);
    }

}
