package be.beme.schn.spring.api.exception;

import be.beme.schn.spring.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dotista on 21-05-16.
 *
 * Handle les esxcpetion dans une classe dédie , et non dans AbstractController, rien que pour extends
 * ResponseEntityExceptionHandler. Si à l'avenir je veux renvoyer un message 'eeruer custom pour
 * une erreur interne à Spring comme  	handleNoSuchRequestHandlingMethod, alors il suffira de faire un
 * @Override
 *
 */

@Slf4j
@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    //par convention les noms de méthode commencent par handle
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(BadParamException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleResultDataAccessException(BadParamException e, HttpServletRequest request)
    {
        if (log.isErrorEnabled()) {
            log.info("Minor exception handled in API", e);
        }
        ErrorResponse response=new ErrorResponse();
        response.setTimestamp(new Date().getTime());//oui c'est bien un timestamp car depuis 1970
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setError(e.getClass().getName());
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        return response;
    }


    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse handleException(DuplicateKeyException e, HttpServletRequest request) {
        if (log.isErrorEnabled()) {
            log.info("Minor exception handled in API", e);
        }
        ErrorResponse response=new ErrorResponse();
        response.setTimestamp(new Date().getTime());
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setError(e.getClass().getName());
        response.setMessage(messageSource.getMessage("duplicate.id.message",null,Locale.ENGLISH));
        response.setPath(request.getRequestURI());
        return response;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleException(Exception e, HttpServletRequest request) {
        if (log.isErrorEnabled()) {
            log.error("Error in API controller", e);
        }
        ErrorResponse response=new ErrorResponse();
        response.setTimestamp(new Date().getTime());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError(e.getClass().getName());
        response.setMessage(e.getMessage());
        response.setPath(request.getRequestURI());
        return response;
    }

}
