package com.codenvy.factory.generator;

import java.util.List;

/**
 * Describe metadata on factories
 * @author Florent Benoit
 */
public class CodenvyYamlDesc implements Comparable<CodenvyYamlDesc> {

    private String       name;
    private String       desc;
    private String       project;
    private String       website;
    private String       factory;
    private String       badgeLink;
    private String       category;
    private List<String> languages;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }


    /**
     * Compares this object with the specified object for order.
     */
    @Override
    public int compareTo(CodenvyYamlDesc o) {
        return this.name.toLowerCase().compareTo(o.name.toLowerCase());
    }


    public String toString() {
        return "CodenvyDesc[" + this.name + "]";
    }

    public String getBadgeLink() {
        return badgeLink;
    }

    public void setBadgeLink(String badgeLink) {
        this.badgeLink = badgeLink;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
