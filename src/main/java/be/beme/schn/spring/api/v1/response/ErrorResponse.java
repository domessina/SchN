package be.beme.schn.spring.api.v1.response;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Created by Dotista on 21-05-16.
 */
@Getter
@Setter
public class ErrorResponse {

    private long timestamp;
    private int status;
    private String error;
//    private String exception;
//    private int code;             internal api code error
    private String message;
    private String path;

}
