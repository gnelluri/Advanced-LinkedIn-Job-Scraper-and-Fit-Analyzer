/*package com.linkedin.scraper.models;

public class profiledata {
    private String name;
    private String headline;

    public profiledata(String name, String headline) {
        this.name = name;
        this.headline = headline;
    }

    public String getName() {
        return name;
    }

    public String getHeadline() {
        return headline;
    }
}*/

package com.linkedin.scraper.models;

public class profiledata {
    private String title;
    private String company;
    private String location;
    private String description;

    public profiledata(String title, String company, String location, String description) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getCompany() { return company; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
}