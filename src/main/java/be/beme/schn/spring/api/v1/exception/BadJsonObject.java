package be.beme.schn.spring.api.v1.exception;

/**
 * Created by Dorito on 05-09-16.
 */
public class BadJsonObject extends Exception {

    public BadJsonObject (String msg){
        super(msg);
    }
}
