package com.example.finalproject;

//article type
public class Article {
    private String title;
    private String url;
    private String section;
    private boolean isFavourite;
    private long id;
    private String[] sectionStr;


    public Article() {
    }

    //parameters within the article
    public Article(String title, String url, String section, boolean isFavourite, long id) {
        this.title = title;
        this.url = url;
        this.section = section;
        this.isFavourite = isFavourite();
        this.id = id;
    }

    public Article(String titleStr, String urlStr, String[] sectionStr, boolean isFavourite, long newId) {
        this.sectionStr = sectionStr;
    }

    //update
    public void updateArticle(String title, String url, String section) {
        this.title = title;
        this.url = url;
        this.section = section;
    }

    //gets title
    public String getTitle() {
        return title;
    }

    //sets title
    public void setTitle(String title) {
        this.title = title;
    }

    //gets  url
    public String getUrl() {
        return url;
    }

    //sets url
    public void setUrl(String url) {
        this.url = url;
    }

    //gets section
    public String getSection() { return section; }

    //sets section
    public void setSection(String section) {
        this.section = section;
    }

    // isFavourite boolean
    public boolean isFavourite () {
        return isFavourite;
    }
    //sets isFavourite boolean
    public void setIsFavourite ( boolean isFavourite){
        this.isFavourite = isFavourite;
    }

    //gets id
    public long getId () {
        return id;
    }
    {
    }
}




