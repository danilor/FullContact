package daniloramirezcr.FullContact;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import daniloramirezcr.SearchEngines.GoogleSearchEngine;
import daniloramirezcr.util.Config;
import daniloramirezcr.util.FileManagement;
import daniloramirezcr.util.Request;

/**
 * Created by danilo on 14/12/2016.
 * @author Danilo Ramírez Mattey
 * @version 0.1
 */
public class FullContact {

    /*
    * Attributes
    * */
    private String apikey = "";
    private String last_search = "";
    private String last_search_type = "";
    private FullContact_Error last_error;
    private ContactData last_data;
    private String look_email_url = "https://api.fullcontact.com/v2/person.!FORMAT!?email=!EMAIL!";
    private String default_format = "json";
    private Config conf = null;




    /*
    * Constructor
    * */
    public FullContact() {
        this.last_error = null;
        this.last_data = null;
        this.conf = new Config();
        this.createEmptyPerson(); // We create the new person object (just in case)
    }


    /*
    * Public set and get methods.
    * */
    public FullContact setApiKey(String k){
        this.apikey = k;
        return this;
    }

    public String getApikey(){
        return this.apikey;
    }

    public ContactData getLastData(){
        return this.last_data;
    }

    /*
    * Public methods
    * */

    public FullContact_Error getLastError(){
        return last_error;
    }

    public FullContact lookByEmail(String email){
        Request r = new Request();
        r.setUrl( this.look_email_url.replaceAll( "!EMAIL!" , email ).replaceAll("!FORMAT!",this.default_format) );
        r.setConnectionMethod( "GET" ).addHeader( "X-FullContact-APIKey" , this.apikey ).setCache( false );
        if(r.execute()){
            this.createPersonFromResult(r.getLastResult()); // Since the person is already created, it will fullfill the methods that we can fullfill.
        }
        this.searchTermGoogleAndAddToPerson( email );
        return this;
    }


    public FullContact searchTermGoogleAndAddToPerson(String term){
        GoogleSearchEngine ge = new GoogleSearchEngine();
        this.last_data.googleResults =  ge.search( term );
        return this;
    }



    /*
    * This method wont make the google search
    * */
    public Boolean lookByEmailAndSaveHTML(String email){
        Boolean success = false;
        Request r = new Request();
        r.setUrl( this.look_email_url.replaceAll( "!EMAIL!" , email ).replaceAll("!FORMAT!","html") );
        r.setConnectionMethod( "GET" ).addHeader( "X-FullContact-APIKey" , this.apikey ).setCache( false );
        String filename = email.replaceAll("\\@","_AT_").replaceAll("\\.","_DOT_") + ".html";
        String folder = conf.read("results_folder");
        FileManagement.verifyFolderOrCreate(folder);
        if(r.executeHTML( folder + "/"  + filename )){
            success = true;
        }
        return success;
    }

    /*
    * Private Methods
    * */

    // Function to create and set the Full Contact error
    private void setError(int id, String des){
        this.last_error = new FullContact_Error();
        this.last_error.setError_id( id ).setError_des( des );
    }



    private void createPersonFromResult(String result){

        // Now we have to convert it to an object
        Gson g = new Gson(); // We create the new object to read the string and turn it into a json object
            /*
            * The following lines are kind of hard to understand,
            * but basically, I am getting each element individually.
            * In the future, if the JSON changes, we will have to change how it is read.
            * */
        JsonObject o = g.fromJson( result , JsonObject.class ); // We create a generic JSON object that is the object we are creating from the JSON string.
        ContactData person = this.last_data;
        // We prepare the person object. This object should be stored later
        try{
            person.requestId = o.get("requestId").getAsString(); // We get the request ID. Just in case.
        }catch (Exception e){}
        try{
            person.likelihood = o.get("likelihood").getAsFloat(); // Same as above.
        }catch (Exception e){}

        /*
        * In the following 3 lines we are getting the photos, the social profiles and the websites as JsonArray.
        * */
        JsonArray photos = null;
        try{
            photos = o.get("photos").getAsJsonArray();
        }catch (Exception e){}
        JsonArray socials = null;
        try{
            socials = o.get("socialProfiles").getAsJsonArray();
        }catch (Exception e){}
        JsonArray websites = null;
        try{
            websites = o.get("contactInfo").getAsJsonObject().get("websites").getAsJsonArray();
        }catch (Exception e){}

        //
        JsonObject contactInfo = null;
        try{ contactInfo = o.get("contactInfo").getAsJsonObject(); }catch (Exception e){}
        JsonObject demographic = null;
        try{ demographic = o.get("demographics").getAsJsonObject(); }catch (Exception e){}


        // The following lines were made to get the main informatino of the profile.
        try{ person.fullName = contactInfo.get("fullName").getAsString(); }catch (Exception e){}
        try{ person.givenName = contactInfo.get("givenName").getAsString(); }catch (Exception e){}
        try{ person.familyName = contactInfo.get("familyName").getAsString(); }catch (Exception e){}
        try{ person.normalizedLocation = demographic.get("locationDeduced").getAsJsonObject().get("normalizedLocation").getAsString(); }catch (Exception e){}
        try{ person.deducedLocation = demographic.get("locationDeduced").getAsJsonObject().get("deducedLocation").getAsString(); }catch (Exception e){}
        try{ person.country_name = demographic.get("locationDeduced").getAsJsonObject().get("country").getAsJsonObject().get("name").getAsString(); }catch (Exception e){}
        try{ person.country_code = demographic.get("locationDeduced").getAsJsonObject().get("country").getAsJsonObject().get("code").getAsString(); }catch (Exception e){}
        try{ person.continent = demographic.get("locationDeduced").getAsJsonObject().get("continent").getAsJsonObject().get("name").getAsString(); }catch (Exception e){}
        try{ person.location_likelihood = demographic.get("locationDeduced").getAsJsonObject().get("likelihood").getAsFloat(); }catch (Exception e){}
        try{ person.location_general= demographic.get("locationGeneral").getAsString(); }catch (Exception e){}


            /*
            * Now we iterate over the photos, the social profiles and the websites to store them.
            * I created separate FullContact_Objects for each of this elements
            * */
        if(photos != null) for( int i = 0 ; i < photos.size() ; i++){
            JsonObject aux = photos.get(i).getAsJsonObject();
            person.addPhoto( aux.get("type").getAsString() , aux.get("typeId").getAsString() , aux.get("typeName").getAsString()  , aux.get("url").getAsString() );
        }
        if(socials != null) for( int i = 0 ; i < socials.size() ; i++){
            JsonObject aux = socials.get(i).getAsJsonObject();
            person.addSocialProfile( aux.get("type").getAsString() , aux.get("typeId").getAsString() , aux.get("typeName").getAsString()  , aux.get("url").getAsString() );
        }

        if(websites != null) for( int i = 0 ; i < websites.size() ; i++){
            JsonObject aux = websites.get(i).getAsJsonObject();
            person.addWebsite( aux.get("url").getAsString() );
        }

        this.last_data = person; // We assign the person to the last person we are getting.

    }

    /*
    * This will create an empty person just in case the FullContact API doesnt return anything. This is because we are adding
    * the Google API search.
    * */
    private void createEmptyPerson(){
        ContactData p = new ContactData();
        p.familyName = "";
        p.fullName = "";
        p.givenName = "";
        p.likelihood = 0;
        p.deducedLocation = "";
        p.location_general = "";
        p.location_likelihood = 0;
        p.country_code = "";
        p.country_name = "";
        p.continent = "";
        this.last_data = p;
    }






}
