package com.nlp;

public class Result {

    private String tfidf;
    private String url;

    public Result(String tfidf, String url) {
        this.tfidf = tfidf;
        this.url = url;
    }

    public String getTfidf() {
        return tfidf;
    }

    public void setTfidf(String tfidf) {
        this.tfidf = tfidf;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
