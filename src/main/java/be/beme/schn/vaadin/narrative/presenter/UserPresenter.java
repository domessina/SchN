package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.persistence.dao.UserDao;
import com.vaadin.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Dotista on 20-08-16.
 */
@Component
@UIScope
public class UserPresenter {

    @Autowired
    UserDao dao;

    @Getter
    @Setter
    private int userId;

    public String getUserPseudo(){
       return dao.getPseudo(userId);
    }

    public int getActualDiagram()
    {
        return dao.getActualDiagram(userId);
    }

    public void setActualDiagram(int diagramId){
        dao.setActualDiagram(userId,diagramId);
    }


}
