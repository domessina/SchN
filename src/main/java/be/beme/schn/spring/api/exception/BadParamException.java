package be.beme.schn.spring.api.exception;

import lombok.Getter;

/**
 * Created by Dotista on 22-05-16.
 */
public class BadParamException extends RuntimeException {


    public BadParamException(String msg)
    {
        super(msg);
    }
}
