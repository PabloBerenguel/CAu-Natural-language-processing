

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {
    public static void main(String[] args) throws Exception {
        String url[] = {"https://www.thesun.co.uk/?s=", "https://www.bbc.co.uk/search?q=", "https://www.skysports.com/search?q="};
        //ArrayList<String> key = new ArrayList<String>();
        Article data = new Article();
        //key.add("salah");
        //key.add("Liverpool");
        //key.add("Champions league");
        Document doc = null;
        Elements element = null;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please type keywords : ");
        String key[] = scanner.nextLine().split(",");
        scanner.close();
        for (int j = 0; j < url.length; j++) {
            for (int i = 0; i < key.length; i++) {
                String urlTmp = url[j] + key[i];
                doc = Jsoup.connect(urlTmp).execute().parse();
            /* } catch (IOException e) {
                   e.printStackTrace();
             }*/
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

        for(int i = 0; i < data.getHowManyData(); i++) {
            System.out.println(data.getDate(i));
            System.out.println(data.getHeadline(i));
            System.out.println(data.getUrl(i));
            System.out.println(data.getSite(i));

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
        formdate += Integer.parseInt(sp[2]) * 10000;
        formdate += Integer.parseInt(sp[0]);

        switch (sp[1]) {
            case "January":
            case "Jan":
                formdate += 100;
                break;
            case "February":
            case "Feb":
                formdate += 200;
                break;
            case "March":
            case "Mar":
                formdate += 300;
                break;
            case "April":
            case "Apr":
                formdate += 400;
                break;
            case "May":
                formdate += 500;
                break;
            case "June":
            case "Jun":
                formdate += 600;
                break;
            case "July":
            case "Jul":
                formdate += 700;
                break;
            case "August":
            case "Aug":
                formdate += 800;
                break;
            case "September":
            case "Sep":
                formdate += 900;
                break;
            case "October":
            case "Oct":
                formdate += 1000;
                break;
            case "November":
            case "Nov":
                formdate += 1100;
                break;
            case "December":
            case "Dec":
                formdate += 1200;
                break;

        }

        return formdate;

    }
}