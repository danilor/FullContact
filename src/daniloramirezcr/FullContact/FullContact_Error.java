package daniloramirezcr.FullContact;

/**
 * Created by danilo on 14/12/2016.
 */
public class FullContact_Error {


    /*
    * Private attributes
    * */
    private String error_des = "";
    private int error_id = 0;

    /*
    * Constructor
    * */
    public void FullContact_Error(){

    }

    /*
    * Public set and get methods
    * */
    public String getError_des() {
        return error_des;
    }

    public FullContact_Error setError_des(String error_des) {
        this.error_des = error_des;
        return this;
    }

    public int getError_id() {
        return error_id;
    }

    public FullContact_Error setError_id(int error_id) {
        this.error_id = error_id;
        return this;
    }



}
