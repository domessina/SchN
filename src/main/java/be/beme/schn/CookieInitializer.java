package be.beme.schn;

import be.beme.schn.vaadin.VaadinUtils;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;

import javax.servlet.http.Cookie;

/**
 * Created by Dotista on 18-04-16.
 */
public class CookieInitializer {

    private static final String USER_ID="userId";

    private CookieInitializer()
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

    public void initCookies() //TODO créer cookie pour le de diagram utilisé en cours. Qujan dl'user se reconnecte ça ne présente pas le choix des diagramme mais ouvre ce dernier diagram.
    {
        Cookie nameCookie = VaadinUtils.getCookieByName(USER_ID);

        if (nameCookie != null) {
            String oldName = nameCookie.getValue();
            nameCookie.setValue("1");
            Notification.show("Updated name in cookie from " + oldName + " to " + "1");

        } else {
            // Create a new cookie
//            nameCookie = new Cookie(NAME_COOKIE, name);
            nameCookie .setComment("Cookie for storing the name of the user");
//            Notification.show("Stored name " + name + " in cookie");
        }

        // Make cookie expire in 2 minutes
        nameCookie.setMaxAge(120);

        // Set the cookie path.
        nameCookie.setPath(VaadinService.getCurrentRequest() .getContextPath());

        // Save cookie
        VaadinService.getCurrentResponse().addCookie(nameCookie);
    }

}
