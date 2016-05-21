package be.beme.schn.spring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
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

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    //par convention les noms de méthode commencent par handle
    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorResponse handleResultDataAccessException(EmptyResultDataAccessException e, HttpServletRequest request)
    {
        ErrorResponse response=new ErrorResponse();
        response.setTimestamp(new Date().getTime());//oui c'est bien un timestamp car depuis 1970
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setError(messageSource.getMessage("notfound", null, Locale.ENGLISH));
        response.setMessage(messageSource.getMessage("resource.notfound", null, Locale.ENGLISH));
        response.setPath(request.getRequestURI());
        return response;
    }

}
