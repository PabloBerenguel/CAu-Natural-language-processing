package com.nlp;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        port(8080);
        get("/search", (req, res) -> {
            Gson gson = new Gson();
            String[] words = req.queryParams("s").split("/+");
            System.out.println("Request : " + req.queryParams("s"));
            ArrayList<Result> results = getResults(words);
            String json = gson.toJson(results);

            System.out.println(json);
            return json;
        });
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
    }

    private static ArrayList<Result> getResults(String[] words) throws Exception {
        String[] url = {"https://www.thesun.co.uk/?s=", "https://www.bbc.co.uk/search?q=", "https://www.skysports.com/search?q="};

        List<List<String>> documents = new ArrayList<>();
        String document;
        List<String> goodUrl = new ArrayList<>();
        Article data = new Article();

        ArrayList<Result> results = new ArrayList<>();
        Document doc = null;
        Elements element = null;

        for (String s : url) {
            for (String word : words) {
                String urlTmp = s + word;
                doc = Jsoup.connect(urlTmp).execute().parse(); // Document에 url 페이지의 데이터를 가져온다.
                if (s.equals("https://www.thesun.co.uk/?s=")) {
                    element = doc.select("div.search-results-wrap");
                    for (Element el : element.select(".teaser-item")) {
                        if (el.select("p").text().toLowerCase().contains(word.toLowerCase())) {
                            data.setHeadline(el.select("p").text());
                            Elements elUrl = el.select(".teaser__copy-container a");
                            data.setUrl(elUrl.first().absUrl("href"));
                            String temp = el.select(".search-date").text();
                            data.setDate(changeDate(temp));
                            data.setSite("The Sun");


                        }
                    }
                }
                if (s.equals("https://www.bbc.co.uk/search?q=")) {
                    element = doc.select("section.search-content");
                    for (Element el : element.select("li[data-result-number]")) {
                        data.setHeadline(el.select("h1").select("a").text());
                        Elements elUrl = el.select("a[href]");
                        data.setUrl(elUrl.first().absUrl("href"));
                        String temp = el.select(".display-date").text();
                        data.setDate(changeDate(temp));
                        data.setSite("BBC");
                    }
                }
                if (s.equals("https://www.skysports.com/search?q=")) {
                    element = doc.select("div.news-list");
                    for (Element el : element.select("div.news-list__item")) {
                        data.setHeadline(el.select("h4").select("a").text());
                        Elements elUrl = el.select("a[href]");
                        data.setUrl(elUrl.first().absUrl("href"));
                        String temp = el.select(".label__timestamp").text();
                        data.setDate(changeDate2(temp));
                        data.setSite("SKYSPORTS");
                    }
                }
            }
        }

        StringBuilder content = new StringBuilder();
        for (int i = 0; i < data.getHowManyData(); i++) {
            content = new StringBuilder();
            try {
                doc = Jsoup.connect(data.getUrl(i)).execute().parse();
                switch (data.getSite(i)) {
                    case "The Sun":
                        element = doc.select("div.article__content");
                        for (Element el : element.select("p")) {
                            content.append(el.text());
                        }
                        data.setContent(content.toString());
                        break;
                    case "BBC":
                        element = doc.select("div#story-body");
                        for (Element el : element.select("p")) {
                            content.append(el.text());
                        }
                        data.setContent(content.toString());
                        break;
                    case "SKYSPORTS":
                        element = doc.select("div.article__body");
                        for (Element el : element.select("p")) {
                            if (!el.hasClass("widge-marketing__text")) {
                                content.append(el.text());
                            }
                        }
                        data.setContent(content.toString());
                        break;
                }
                if (data.getContent(i).length() > 0) {
                    document = data.getContent((i)).replaceAll("\\s+", ",");
                    goodUrl.add(data.getUrl(i));
                    documents.add(new ArrayList<String>(Arrays.asList(document.split(","))));
                } else
                    System.out.println("No content: " + data.getUrl(i));
            } catch (Exception e) {
                System.out.println("Something went wrong.: " + e);
            }
        }
        for (int i = 0; i < documents.size(); i++) {
            DecimalFormat df = new DecimalFormat("#.####");
            TFIDF calculator = new TFIDF();
            double tfidf = calculator.tfIdf(documents.get(i), documents, words[0]);
            results.add(new Result(df.format(tfidf), goodUrl.get(i)));
        }
        System.out.println("End Request");
        return results;
    }

    private static int changeDate2(String date) {
        date = date.substring(0, 2) + date.substring(2 + 1);
        date = date.substring(0, 4) + date.substring(4 + 1);
        String year = date.substring(4, 8);
        String month = date.substring(2, 4);
        String day = date.substring(0, 2);
        String fdate = year + month + day;

        int mydate = Integer.parseInt(fdate);
        return mydate;
    }

    private static int changeDate(String date) {
        int formdate = 0;
        String[] sp = date.split(" ");
        formdate = 0;
        return formdate;
    }

}