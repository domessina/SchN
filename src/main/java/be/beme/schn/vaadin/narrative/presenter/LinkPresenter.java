package be.beme.schn.vaadin.narrative.presenter;

import be.beme.schn.persistence.Dao;
import be.beme.schn.vaadin.narrative.view.UpDownView;
import be.beme.schn.vaadin.narrative.view.NarrativeView;
import com.vaadin.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

/**
 * Created by Dotista on 19-04-16.
 */
@Component
@UIScope
public class LinkPresenter implements UpDownPresenter  {

    private UpDownView view;

    @Override
    public void setView(NarrativeView narrativeView) {
        this.view=(UpDownView)narrativeView;
    }

    @Override
    public Dao getDaoService() {
        return null;
    }
}
