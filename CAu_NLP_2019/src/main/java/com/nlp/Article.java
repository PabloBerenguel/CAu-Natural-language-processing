package com.nlp;

import java.util.ArrayList;

public class Article {

    public Article() {
        date = new ArrayList<Integer>();
        Headline = new ArrayList<String>();
        Url = new ArrayList<String>();
        Site = new ArrayList<String>();
        Content = new ArrayList<String>();
    }

    private ArrayList<Integer> date;
    private ArrayList<String> Headline;
    private ArrayList<String> Url;
    private ArrayList<String> Site;
    private ArrayList<String> Content;

    public void setDate(int num) {
        date.add(num);
    }

    public void setHeadline(String head) {
        Headline.add(head);
    }

    public void setUrl(String url) {
        Url.add(url);
    }

    public void setSite(String site) {
        Site.add(site);
    }

    public void setContent(String content) {
        Content.add(content);
    }


    public int getHowManyData() {
        return Headline.size();
    }

    public int getDate(int num) {
        return date.get(num);
    }

    public String getHeadline(int num) {
        return Headline.get(num);
    }

    public String getUrl(int num) {
        return Url.get(num);
    }

    public String getSite(int num) {
        return Site.get(num);
    }

    public String getContent(int num) {
        return Content.get(num);
    }
}