package com.codenvy.factory.generator;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Florent Benoit
 */
public class CodenvyYamlVisitor extends SimpleFileVisitor<Path> {

    public static final String CODENVY_YAML_FILE = "codenvy.yml";

    private List<Path> codenvyYamlFiles;

    public CodenvyYamlVisitor() {
        this.codenvyYamlFiles = new ArrayList<>();
    }


    @Override
    public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs)
            throws IOException {
        if (Files.isDirectory(path)) {
            throw new IllegalStateException("Shouldn't visit directory" + path.toAbsolutePath().toString());
        }

        // add matching files
        if (CODENVY_YAML_FILE.equals(path.getFileName().toString())) {
            codenvyYamlFiles.add(path);
        }
        return super.visitFile(path, attrs);
    }


    public List<Path> getCodenvyYamlFiles() {
        return codenvyYamlFiles;
    }



}