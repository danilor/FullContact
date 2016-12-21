package daniloramirezcr.util;

/**
 * Created by danilo on 14/12/2016.
 */


import java.net.*; // This is for the network function
import java.io.*; // This is for the option to read the buffer
import java.util.HashMap;
import java.util.*;



/*
* This class is suppose to manage all request to other urls from any JAVA application. At the beginning it will be very simple, but we are going to
* add more and more options as we need them. Stay tune.
* */

public class Request {
    /*
    * Private attributes
    * */
    private String url = "";
    private String result = ""; // It will store the last result
    private String error = "";
    private String conectionMethod = "GET";
    private HashMap headers = null;
    private Boolean use_cache = false;
    private String user_agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11";


    /*
    * Public get and set methods
    * */
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getCache() {
        return this.use_cache;
    }

    public void setCache(Boolean c) {
        this.use_cache = c;
    }


    public String getConectionMethod(){
        return this.conectionMethod;
    }
    public Request setConnectionMethod(String m){
        this.conectionMethod = m;
        return this;
    }

    public String getLastResult(){
        return this.result;
    }
    /*
    * Public functions
    * */

    public Boolean execute(){
        Boolean success = false;
        try{
            // We set the url
            URL request_object = new URL( this.getUrl() );
            HttpURLConnection connection = (HttpURLConnection) request_object.openConnection();
            connection.setRequestMethod( this.conectionMethod );
            connection.setRequestProperty("User-Agent", this.user_agent );
            connection.setUseCaches( this.use_cache );
            connection.setDoInput( true );
            connection.setDoOutput( true );
            //We are going to iterate over all headers.
            if( this.headers != null ){
                Set set = this.headers.entrySet();
                Iterator i = set.iterator();
                while(i.hasNext()) {
                    Map.Entry me = (Map.Entry)i.next();
                    //System.out.print(me.getKey() + ": ");
                    //System.out.println(me.getValue());
                    connection.setRequestProperty( me.getKey().toString() , me.getValue().toString() );

                }
            }
            InputStream is = connection.getInputStream();
            /*READING THE ACTUAL CONTENCT*/
            this.result = this.readBufferInToString( is );

            //BufferedReader in = new BufferedReader( new InputStreamReader(request_object.openStream()));
            //this.result = in.toString();
            success = true;
        }catch (Exception e){
            this.setError( e.getMessage() );
            success = false;

        }finally {

        }
        return success;
    }

    /*
    * This will add a header
    * */
    public Request addHeader(String key , String value){
        if(this.headers == null){
            this.headers = new HashMap();
        }
        this.headers.put(key,value);
        return this;
    }
    /*
    * This will clean all headers
    * */
    public Request cleanHeaders(){
        this.headers = null;
        return this;
    }

    /*
    * This will remove one single header.
    * */
    public Request removeHeader(String key){
        if(this.headers == null){
            this.headers = new HashMap();
        }
        try{
            this.headers.remove( key );
        }catch (Exception e){
            this.setError( e.getMessage() );
        }
        return this;
    }

    /*
    * This will execute the same call and save the result in a storage file
    * */
    public Boolean executeHTML(String name){
        Boolean r = false;
        if(r = this.execute()){
            FileManagement.writeToFile( name , this.result ); // We send the result to a "name" file.
        }
        return r;
    }


    /*
    * Private methods
    * */

    private void setError(String e){
        this.error = e;
    }
    private String readBufferInToString(InputStream is){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }catch (Exception e){
            return "";
        }

        return sb.toString();
    }


}
