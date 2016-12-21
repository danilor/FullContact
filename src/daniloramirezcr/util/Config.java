package daniloramirezcr.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * This class is suppose to work in a way that we can easily read the properties file.
 * @author Crunchify.com
 */

public class Config {
    /*
    * Class attributes
    * */
    private InputStream inputStream;

    /*
    * This is the function that actually reads the value
    * */
    public String getValue(String key) throws IOException {
        String r = "";
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propFileName + "' not found in the classpath");
            }
            r = prop.getProperty(key);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return r;
    }

    /*
    * This function was made to avoid the error throw exception outside this class, but if somebody wants, they can still use the other method.
    * */
    public String read(String key){
        String result = "";
        try{
            result = this.getValue(key); // We read the property key
        }catch (Exception e){
            result = "";
        } finally { }
        return result;
    }

    /*
    * This function was made to avoid the error throw exception outside this class, but if somebody wants, they can still use the other method.
    * This overwrite uses a default value
    * */
    public String read(String key,String default_value){
        String result = default_value;
        try{
            this.getValue(key);
        }catch (Exception e){
            result = default_value;
        } finally { }
        return result;
    }

}