

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {
    public static void main(String[] args) throws Exception {
        String url[] = {"https://www.thesun.co.uk/?s=", "https://www.bbc.co.uk/search?q=", "https://www.skysports.com/search?q="};

        List<List<String>> documents = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        List<String> result2 = new ArrayList<>();
        String document;
        List<String> goodUrl = new ArrayList<>();
        Article data = new Article();

        Document doc = null;
        Elements element = null;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please type keywords : ");
        String key[] = scanner.nextLine().split(",");
        scanner.close();
        for (int j = 0; j < url.length; j++) {
            for (int i = 0; i < key.length; i++) {
                String urlTmp = url[j] + key[i];
                doc = Jsoup.connect(urlTmp).execute().parse(); // Document에 url 페이지의 데이터를 가져온다.
                if (url[j].equals("https://www.thesun.co.uk/?s=")) {
                    element = doc.select("div.search-results-wrap");
                    for (Element el : element.select(".teaser-item")) {
                        if (el.select("p").text().toLowerCase().contains(key[i].toLowerCase())) {
                            data.setHeadline(el.select("p").text());
                            Elements elUrl = el.select(".teaser__copy-container a");
                            data.setUrl(elUrl.first().absUrl("href"));
                            String temp = el.select(".search-date").text();
                            data.setDate(changeDate(temp));
                            data.setSite("The Sun");


                        }
                    }
                }
                if (url[j].equals("https://www.bbc.co.uk/search?q=")) {
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
                if (url[j].equals("https://www.skysports.com/search?q=")) {
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

        String content = "";
        for(int i = 0; i < data.getHowManyData(); i++) {
            content = "";
            try {
                doc = Jsoup.connect(data.getUrl(i)).execute().parse();
                if (data.getSite(i) == "The Sun") {
                    element = doc.select("div.article__content");
                    for (Element el : element.select("p")) {
                        content += el.text();
                    }
                    data.setContent(content);
                } else if (data.getSite(i) == "BBC") {
                    element = doc.select("div#story-body");
                    for (Element el : element.select("p")) {
                        content += el.text();
                    }
                    data.setContent(content);
                } else if (data.getSite(i) == "SKYSPORTS") {
                    element = doc.select("div.article__body");
                    for (Element el : element.select("p")) {
                        if (!el.hasClass("widge-marketing__text")) {
                            content += el.text();
                        }
                    }
                    data.setContent(content);
                }
                if (data.getContent(i).length() > 0){
                    /*
                    System.out.println(data.getDate(i));
                    System.out.println(data.getHeadline(i));
                    System.out.println(data.getUrl(i));
                    System.out.println(data.getSite(i));
                    */
                    document = data.getContent((i)).replaceAll("\\s+",",");
                    goodUrl.add(data.getUrl(i));
                    documents.add(new ArrayList<String>(Arrays.asList(document.split(","))));
                }
                else
                    System.out.println("No content: " + data.getUrl(i));
            }
            catch (Exception e) {
                System.out.println("Something went wrong.: " + e);
            }
        }
        for (int i = 0; i < documents.size(); i++){
            DecimalFormat df = new DecimalFormat("#.####");
            TFIDF calculator = new TFIDF();
            double tfidf = calculator.tfIdf(documents.get(i), documents, key[0]);
            result.add(Arrays.asList((df.format(tfidf)), goodUrl.get(i)));
        }
        for (int i = 0; i < result.size(); i++){
            System.out.println("TF-IDF: " + result.get(i).get(0) + " Url: " + result.get(i).get(1));
        }
        double max;
        int pos;
        while (result.size() > 0){
            pos = 0;
            max = Double.parseDouble(result.get(0).get(0));
            for (int i2 = 0; i2 < result.size(); i2++){
                if (Double.compare(max,  Double.parseDouble(result.get(i2).get(0))) < 0){
                    max = Double.parseDouble(result.get(i2).get(0));
                    pos = i2;
                }
            }
            result2.add(result.get(pos).get(1));
            result.remove(pos);
        }
        for (int i = 0; i < result2.size(); i++){
            System.out.println("Url: " + result2.get(i));
        }

    }

    public static int changeDate2(String date) {
        date = date.substring(0, 2) + date.substring(2 + 1);
        date = date.substring(0, 4) + date.substring(4 + 1);
        String year = date.substring(4, 8);
        String month = date.substring(2,4);
        String day = date.substring(0, 2);
        String fdate = year + month + day;

        int mydate = Integer.parseInt(fdate);
        return mydate;
    }

    public static int changeDate(String date) {
        int formdate = 0;
        String sp[] = date.split(" ");
        formdate = 0;
        return formdate;

    }
}