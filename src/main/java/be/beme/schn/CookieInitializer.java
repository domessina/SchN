package be.beme.schn;

import be.beme.schn.vaadin.VaadinUtils;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;

import javax.servlet.http.Cookie;

/**
 * Created by Dotista on 18-04-16.
 */
public class CookieInitializer {



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

    public static void initCookies() //TODO créer cookie pour le de diagram utilisé en cours. Qujan dl'user se reconnecte ça ne présente pas le choix des diagramme mais ouvre ce dernier diagram.
    {
      userCookie();
        diagramCookie();
    }

    private static void userCookie()
    {
        Cookie cookie = VaadinUtils.getCookieByName(Constants.CK_USER_ID);

        if (cookie != null) {
//            String oldName = cookie.getValue();
            cookie.setValue("1");

        } else {
            cookie = new Cookie(Constants.CK_USER_ID, "1");
            cookie .setComment("Cookie for storing the id of the user");
//            cookie.setHttpOnly(true);
//            cookie.setSecure(true);
            cookie.setMaxAge(604800);
            // Set the cookie path.
            cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
        }

        // Make cookie expire in one week
//        cookie.setMaxAge(604800);




        // Save cookie
        VaadinService.getCurrentResponse().addCookie(cookie);
    }
    private static void diagramCookie()
    {
        Cookie cookie = VaadinUtils.getCookieByName(Constants.CK_DIAGRAM_ID);

        if (cookie != null) {
            cookie.setValue("3");

        } else {
            cookie = new Cookie(Constants.CK_DIAGRAM_ID, "3");
            cookie .setComment("Cookie for storing the id of the user");
            cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
        }
        //if no Max age specified , it's session scope
        cookie.setMaxAge(604800);


        VaadinService.getCurrentResponse().addCookie(cookie);
    }


}
