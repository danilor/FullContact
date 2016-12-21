package daniloramirezcr.SearchEngines;

import java.net.URLEncoder;
import daniloramirezcr.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danilo on 21/12/2016.
 * This class should be able to make searchs into google directly into the HTML. We are not using the API in this class.
 */
public class GoogleSearchEngine {

    private String base_url_google = "https://www.google.com/search?source=custom&q=!SEARCH!";
    private String default_encoding = "UTF-8";

    public List<GoogleResult> search(String term){
        List<GoogleResult> results_google = new ArrayList<GoogleResult>();
        String url = "";
        try{
            url = this.base_url_google.replaceAll("!SEARCH!", URLEncoder.encode(term,this.default_encoding) );
        }catch (Exception e){}
        Request r = new Request();
        r.setUrl( url );
        r.setConnectionMethod("GET").setCache( false );
        if(r.execute()){
            String result = r.getLastResult();
            results_google = this.getResultsFromHTML( result );
        }
        return results_google;

    }

    private List<GoogleResult> getResultsFromHTML(String html ){
        List<GoogleResult> results_google = new ArrayList<GoogleResult>();
        Document doc = Jsoup.parse( html );
        Elements results = doc.select("div.g");
        for( int i = 0 ; i < results.size() ; i++ ){
            Element result = results.get( i );

            try{
                Elements h3sa = result.select("h3.r a");
                Element a = h3sa.get(0);
                String url = a.attr("href");
                String title = a.text();
                String content = result.select(".s .st").text();
                GoogleResult aux = new GoogleResult();
                aux .   title       = title;
                aux .   description = content;
                aux .   url         = url;
                results_google.add( aux );

            }catch (Exception e){}

        }
        return results_google;

    }

}
