package be.beme.schn.vaadin.narrative;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.vaadin.TableCrud;
import be.beme.schn.vaadin.WindowCrud;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import java.util.List;

/**
 * Created by Dotista on 06-04-16.
 */
public class TraitTableCrud extends TableCrud {

    private List<Trait> traits;

    public TraitTableCrud(String caption, List<Trait> traits) {
        super(caption);
        this.traits=traits;

        FormLayout formLayout= new FormLayout();
        formLayout.addComponent(new TextField("Name"));
        setSubmissionWindow(new TraitWindow("Trait",formLayout));

        setPageLength(4);
        setSelectable(true);
        setWidth(100, Unit.PERCENTAGE);
        addContainerProperty("Trait",Trait.class,null);
        buildTable();
    }

    private void buildTable()
    {
        try
        {
            for(Trait trait:traits)
            {
                addItem(new Object[]{trait},trait);
            }

        }
        catch (NullPointerException e)
        {
            System.out.println("This narrative component has no traits");                         //TODO à n'afficher qye pour un certain niveau de debug
        }

    }

//-----------------------------------------------------------------------

    private class TraitWindow extends WindowCrud implements FieldEvents.FocusListener {

        private Trait traitCrud;
        private Object targetId;
        private boolean objCreated;
        private boolean objUpdated;
        private boolean objDeleted;


        public TraitWindow(String caption, FormLayout formLayout) {
            super(caption, formLayout);
            addFocusListener(this);
        }


        @Override
        protected void setItem(Object itemId, boolean delete)
        {
            if(!delete)
            {
                targetId=itemId;
                traitCrud =(Trait)(getItem(itemId).getItemProperty("Trait").getValue());
                ((TextField)this.formLayout.getComponent(0)).setValue(traitCrud.getName());
            }
            else
            {
                traitCrud=(Trait)(getItem(itemId).getItemProperty("Trait").getValue());
                removeItem(itemId);
                objCreated=false;
                objUpdated=false;
                objDeleted=true;
            }

        }

        @Override
        public boolean isObjCreated() {
            return objCreated;
        }

        @Override
        public boolean isObjUpdated() {
            return objUpdated;
        }

        @Override
        public boolean isObjDeleted() {
            return objDeleted;
        }

        @Override
        public Object getCrudObj() {
            return traitCrud;
        }

        @Override
        public void buttonClick(Button.ClickEvent event) {

            TextField tf=(TextField)this.formLayout.getComponent(0);

            if(targetId ==null)
            {
                traitCrud= new Trait(tf.getValue(),0);
                addItem(new Object[]{traitCrud},traitCrud);
//                TraitTableCrud.super.notifyCreated(trait);
                objCreated=true;
                objUpdated=false;
                objDeleted=false;
            }
            else
            {
                traitCrud.setName(tf.getValue());
              getItem(targetId).getItemProperty("Trait").setValue(traitCrud);
//                TraitTableCrud.super.notifyUpdated(traitCrud);
                targetId=null;
                objCreated=false;
                objUpdated=true;
                objDeleted=false;
            }

            close();
        }

        @Override
        public void focus(FieldEvents.FocusEvent event) {
            if(objDeleted==true)
            {
                close();
            }
        }
    }

}
