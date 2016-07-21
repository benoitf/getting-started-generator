package com.codenvy.factory.generator;

import com.beust.jcommander.Parameter;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Florent Benoit
 */
public class Generator {

    /**
     * allow to extract factory id
     */
    private static final Pattern FACTORY_PATTERN = Pattern.compile(".*?id=(.*)");

    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = { "-s", "--sourceFolder" }, description = "Path to the source of factories defined in che-website folder from https://github.com/codenvy/factories/tree/factory-2.0")
    private File sourceFolder;

    @Parameter(names = { "-gs", "--getting-started" }, description = "Path to the eclipse.org/che getting started index.html page coming from git.eclipse.org/gitroot/www.eclipse.org/che.git")
    private File gettingStartedFile;

    public void start() throws IOException, NoSuchAlgorithmException {

        if (sourceFolder == null) {
            throw new IllegalStateException("Missing source folder with -s or --sourceFolder");
        }
        if (gettingStartedFile == null) {
            throw new IllegalStateException("Missing getting started path with -gs or --getting-started");
        }

        if (!"che-website".equals(sourceFolder.getName())) {
            throw new IllegalStateException("the source folder should be referencing che-website");
        }

        if (!"index.html".equals(gettingStartedFile.getName())) {
            throw new IllegalStateException("the getting started path should be referencing index.html of getting started");
        }

        // root path
        Path sourcePath = sourceFolder.toPath();


        Path gettingStartedHtmlPath = gettingStartedFile.toPath();
        Path imagesGettingStartedPath = gettingStartedHtmlPath.getParent().resolve("images");


        if (!Files.exists(gettingStartedHtmlPath)) {
            throw new IllegalStateException(String.format("The getting started page is not found at location %s", gettingStartedHtmlPath));
        }

        // delete all previous images
        if (Files.exists(imagesGettingStartedPath)) {
            delete(imagesGettingStartedPath);
        }

        // create folder
        Files.createDirectory(imagesGettingStartedPath);

        generateJson(sourcePath, "factories-json.mustache", gettingStartedHtmlPath.getParent().resolve("scripts/factories.json"), imagesGettingStartedPath);

        System.out.println("Successfully generated.");

    }



    void generateJson(Path initFolder, String templateName, Path jsonOutput, Path imagesOutputfolder) throws IOException, NoSuchAlgorithmException {

        // search codenvy yaml files
        CodenvyYamlVisitor visitor = new CodenvyYamlVisitor();
        Files.walkFileTree(initFolder, visitor);

        // collect files
        List<Path> collectedFiles = visitor.getCodenvyYamlFiles();

        // List of descriptions of factories
        List<CodenvyDesc> codenvyDescs = new ArrayList<>();

        // parse yaml files are read the descriptions
        for (Path codenvyYamlFile: collectedFiles) {
            CodenvyYamlParser codenvyYamlParser = new CodenvyYamlParser();
            CodenvyDesc codenvyDesc = codenvyYamlParser.analyze(Files.newBufferedReader(codenvyYamlFile, Charset.defaultCharset()));

            // search logo
            Path parent = codenvyYamlFile.getParent();
            Path sameFolderLogo = parent.resolve("logo.png");
            if (Files.exists(sameFolderLogo)) {
                codenvyDesc.setSourceLogo(sameFolderLogo);
            } else {
                Path upfolderLogo = parent.getParent().resolve("logo.png");
                if (Files.exists(upfolderLogo)) {
                    codenvyDesc.setSourceLogo(upfolderLogo);
                }
            }

            // extract factory ID
            Matcher matcher = FACTORY_PATTERN.matcher(codenvyDesc.getFactory());
            if (!matcher.matches()) {
                throw new IllegalStateException(String.format("Invalid factory pattern found for yaml file with name %s", codenvyDesc));
            }
            String codenvyFactoryId = matcher.group(1);

            // copy logo to output folder
            Path imageOutput = imagesOutputfolder.resolve(codenvyFactoryId + ".png");
            codenvyDesc.setRenderingLogo("images/" + imageOutput.getFileName().toString());
            Files.copy(codenvyDesc.getSourceLogo(), imageOutput);

            // add parsed object
            codenvyDescs.add(codenvyDesc);
        }


        // sort by name
        Collections.sort(codenvyDescs);
        for (int i=0; i < codenvyDescs.size() -1; i++) {
            CodenvyDesc codenvyDesc = codenvyDescs.get(i);
            codenvyDesc.setHasMoreElements(true);
        }

        // init mustache by loading correct template
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("com/codenvy/factory/generator/" + templateName);

        // create context
        MustacheContext context = new MustacheContext(codenvyDescs);

        // Return rendering of template
        StringWriter writer = new StringWriter();
        mustache.execute(writer, context).flush();
        String content = writer.toString();

        Files.write(jsonOutput, content.getBytes());

    }


    /**
     * Helper method to delete all images
     * @param path
     * @throws IOException
     */
    void delete(Path path) throws IOException {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });

    }

}
