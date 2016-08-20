package be.beme.schn.vaadin;

import be.beme.schn.Constants;
import be.beme.schn.vaadin.VaadinUtils;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;

import javax.servlet.http.Cookie;

/**
 * Created by Dotista on 18-04-16.
 */
public final class CookieManager {



    private CookieManager()
    {

    }

    /*
    * getstate
    * close (session)
    *
    * addrequestHandler fournir une reponse aux requetes non gérere par defaut par vaadin
    * Page.getwebBrowqser
    * getCsrfToken
    *
    * getPendingAccessQueue
    * UI.access(Runnable) for big changes on UI, threatsafesi pluierus actions uesr alors que pas charge completement(ou si user spam bouton imortant)
    * lock/unloack/haslock
    * */

    //TODO créer cookie pour le de diagram utilisé en cours. Qujan dl'user se reconnecte ça ne présente pas le choix des diagramme mais ouvre ce dernier diagram.


    public final static void addCookieUserId(String value) {

        Cookie cookie = new Cookie(Constants.CK_USER_ID, value);
        cookie .setComment("Cookie for storing the id of the user");
        cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
        VaadinService.getCurrentResponse().addCookie(cookie);
    }


    public final static void addCookieDiagramId(String value) {

        Cookie cookie = new Cookie(Constants.CK_DIAGRAM_ID, value);
        cookie .setComment("Cookie for storing the id of the user");
        cookie.setPath(VaadinService.getCurrentRequest().getContextPath());

        //if no Max age specified , it's session scope
        cookie.setMaxAge(604800);

        // Put cookie to client
        VaadinService.getCurrentResponse().addCookie(cookie);
    }


}
