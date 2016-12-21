/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daniloramirezcr.util;
import javax.mail.internet.*;

/**
 *
 * @author danilo
 */
public class Validation {
   
   public static Boolean validEmailAddress( String email ){
        boolean result = true;
        try {
           InternetAddress emailAddr = new InternetAddress(email);
           emailAddr.validate();
        } catch (AddressException ex) {
           result = false;
        }
        return result;
   }
}
