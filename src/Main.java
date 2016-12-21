/**
 * Created by danilo on 14/12/2016.
 */


import UI.MainSearchForm;
import daniloramirezcr.FullContact.FullContact;
import daniloramirezcr.FullContact.ContactData;
import daniloramirezcr.util.Config;
import daniloramirezcr.util.Console;

public class Main {

    public static void main2( String[] args ){
        MainSearchForm ms = new MainSearchForm();

        ms.setVisible( true );
        
        
    }
    
   
    public static void main( String[] args ){
        Console.l("Welcome to the FullContact Magic Search System");
        //String e = Console.readString("Please insert the email you want to search for: ");
        Config c = new Config();
        String key = c.read("fullcontact_key");
        FullContact fc = new FullContact();
        fc.setApiKey( key );

        Console.l("Please wait while we harvest the information...");
        fc.lookByEmail( "zuhrig@lbaabogados.com" );
        //fc.lookByEmail( e );
        ContactData person = fc.getLastData();

        if(person != null){
            /*
        * Its time to show all the person information.
        * */
            Console.l("FullName: " + person.fullName);
            Console.l("Family Name: " + person.familyName);
            Console.l("Given Name: " + person.givenName);
            Console.l("Request ID: " + person.requestId);
            Console.l("Location General: " + person.location_general);
            Console.l("Country Name: " + person.country_name);
            Console.l("Country Cod: " + person.country_code);
            Console.l("Continent: " + person.continent);
            if( person.photos.size() > 0 ){
                Console.l( "Photos found." );
                for( int i = 0 ; i < person.photos.size() ; i++ ){
                    Console.l( " - " +  person.photos.get(i).typeName + " :: " + person.photos.get(i).url );
                }
            }
            if( person.social.size() > 0 ){
                Console.l( "Social Profiles found." );
                for( int i = 0 ; i < person.social.size() ; i++ ){
                    Console.l( " - " + person.social.get(i).typeName + " :: " + person.social.get(i).url );
                }
            }
            if( person.websites.size() > 0 ){
                Console.l( "Websites found." );
                for( int i = 0 ; i < person.websites.size() ; i++ ){
                    Console.l( " - " + person.websites.get(i).url );
                }
            }
            if( person.googleResults.size() > 0 ){
                Console.l( "Google Results found." );
                for( int i = 0 ; i < person.googleResults.size() ; i++ ){
                    Console.space();
                    Console.l( "TITLE: " + person.googleResults.get(i).title );
                    Console.l( "URL: " + " - " + person.googleResults.get(i).url );
                    Console.l( "DOMAIN: " + " - " + person.googleResults.get(i).getDomainName() );
                    Console.l(  person.googleResults.get(i).description );
                }
            }
        }else{
            Console.e("No information found for this email.");
        }



    }
    
}
