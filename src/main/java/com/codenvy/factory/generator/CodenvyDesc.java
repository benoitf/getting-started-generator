package com.codenvy.factory.generator;

import java.nio.file.Path;

/**
 * Add management of the logo
 * @author Florent Benoit
 */
public class CodenvyDesc extends CodenvyYamlDesc {

    private Path sourceLogo;
    private String renderingLogo;


    public Path getSourceLogo() {
        return sourceLogo;
    }

    public void setSourceLogo(Path sourceLogo) {
        this.sourceLogo = sourceLogo;
    }


    public String getRenderingLogo() {
        return renderingLogo;
    }

    public void setRenderingLogo(String renderingLogo) {
        this.renderingLogo = renderingLogo;
    }
}
