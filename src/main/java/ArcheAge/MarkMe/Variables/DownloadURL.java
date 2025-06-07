package ArcheAge.MarkMe.Variables;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class DownloadURL {
    public static void main(String[] args) {

    }
    public static String getURL(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element element = doc.select("[name=originalLink]").first(); // Ищем элемент с name="username"
            if (element != null) {
                String value = element.attr("value"); // Если это input, берем value
                List<String> split = new ArrayList<>(List.of(value.split("=")));
                split.remove(0);
                split.remove(0);
                List<String> v = List.of(split.get(0).split(">"));
                String result = v.get(0).substring(1, v.get(0).length() - 1);
                return result;}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
