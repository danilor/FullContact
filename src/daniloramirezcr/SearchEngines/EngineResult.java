package daniloramirezcr.SearchEngines;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by danilo on 21/12/2016.
 */
public class EngineResult {
    public String title = "", url = "", description = "", type_name = "", type_abr = ""; // This are the variables that are going to be used for each Google Result

    public String getDomainName() {
        URI uri = null;
        try{
            uri = new URI( this.url );
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        }catch (URISyntaxException e){
            return "";
        }
    }
}
