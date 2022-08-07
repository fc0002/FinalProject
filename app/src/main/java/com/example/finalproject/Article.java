package com.example.finalproject;

public class Article {

    private String articleName;
    private String title;
    private String url;
    private String section;
    private boolean isFavourite;
    private long id;


    public Article() {
    }

    public Article(String articleName, String title, String Url, String section, boolean isFavourite, long id) {
        this.articleName = articleName;
        this.title = title;
        this.url = url;
        this.section = section;
        this.isFavourite = isFavourite();
        this.id = id;
    }

    public void updateArticle(String articleName, String title, String url, String section) {
        this.articleName = articleName;
        this.title = title;
        this.url = url;
        this.section = section;
    }

    public String getArticleName() {
        return articleName();
    }

    private String articleName() {
        return null;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String url) {
        this.url = section;


    }
    public boolean isFavourite(){
        return isFavourite;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public long getId() {
        return id;
    }
}