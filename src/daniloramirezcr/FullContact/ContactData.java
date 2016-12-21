package daniloramirezcr.FullContact;

import daniloramirezcr.SearchEngines.GoogleResult;

import java.util.*;

/**
 * Created by danilo on 14/12/2016.
 */
public class ContactData {
    // All this attributes should be fullfill from the JSON
    public String requestId, fullName, givenName, familyName,
            normalizedLocation, deducedLocation, country_name, country_code,
            continent,
            location_general;
    public float likelihood, location_likelihood;
    public List<ContactWebsite> websites = new ArrayList<ContactWebsite>();
    public List<ContactPhoto> photos = new ArrayList<ContactPhoto>();
    public List<ContactSocialProfile> social = new ArrayList<ContactSocialProfile>();
    public List<GoogleResult> googleResults = new ArrayList<GoogleResult>();

    /* Constructor */
    public void FullContact_Data(){

    }

    public ContactData addPhoto(String type, String typeId, String typeName, String url){
        ContactPhoto aux = new ContactPhoto();
        aux . type      = type;
        aux . typeId    = typeId;
        aux . typeName  = typeName;
        aux . url       = url;
        this.photos.add( aux );
        return this;
    }

    public ContactData addGoogleResult(String url, String title, String content){
        GoogleResult aux = new GoogleResult();
        aux . url           = url;
        aux . title         = title;
        aux . description   = content;
        this.googleResults.add( aux );
        return this;
    }

    public ContactData addSocialProfile(String type, String typeId, String typeName, String url){
        ContactSocialProfile aux = new ContactSocialProfile();
        aux . type      = type;
        aux . typeId    = typeId;
        aux . typeName  = typeName;
        aux . url       = url;
        this.social.add( aux );
        return this;
    }

    public ContactData addWebsite(String url){
        ContactWebsite aux = new ContactWebsite();
        aux . url = url;
        this.websites.add( aux );
        return this;
    }


}
