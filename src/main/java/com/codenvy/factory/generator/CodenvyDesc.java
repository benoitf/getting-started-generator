package com.codenvy.factory.generator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Add management of the logo
 * @author Florent Benoit
 */
public class CodenvyDesc extends CodenvyYamlDesc {

    private Path sourceLogo;
    private String renderingLogo;

    private boolean hasMoreElements;

    private boolean badgedRepository;


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

    public boolean isHasMoreElements() {
        return hasMoreElements;
    }

    public void setHasMoreElements(boolean hasMoreElements) {
        this.hasMoreElements = hasMoreElements;
    }


    public List<CodenvyLanguage> getLanguagesFormat() {
        List<String> languages = getLanguages();

        List<CodenvyLanguage> codenvyLanguages = new ArrayList<>();
        if (languages == null) {
            return Collections.emptyList();
        }
        for (int i = 0; i < languages.size(); i++) {
            boolean hasMore = !(i == languages.size() - 1);
            codenvyLanguages.add(new CodenvyLanguage(languages.get(i), hasMore));
        }

        return codenvyLanguages;
    }

    public boolean isBadgedRepository() {
        return getBadgeLink() != null && !"".equals(getBadgeLink());
    }


    class CodenvyLanguage {
        private String name;
        private boolean hasMoreElements;

        public CodenvyLanguage(String name, boolean hasMoreElements) {
            this.name = name;
            this.hasMoreElements = hasMoreElements;
        }


        public String getName() {
            return name;
        }

        public boolean isHasMoreElements() {
            return hasMoreElements;
        }
    }
}
