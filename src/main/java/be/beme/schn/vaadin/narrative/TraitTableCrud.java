package be.beme.schn.vaadin.narrative;

import be.beme.schn.narrative.component.Trait;
import be.beme.schn.vaadin.TableCrud;
import be.beme.schn.vaadin.WindowCrud;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dotista on 06-04-16.
 */
public class TraitTableCrud extends TableCrud<Trait> {


    public TraitTableCrud(String caption) {
        super(caption);

        FormLayout formLayout= new FormLayout();
        formLayout.addComponent(new TextField("Name"));
        setSubmissionWindow(new TraitWindow("Trait",formLayout));

        setPageLength(4);
        setSelectable(true);
        setWidth(100, Unit.PERCENTAGE);
        addContainerProperty("Trait",Trait.class,null);
    }

    @Override
    public void fillTable(List<Trait> traits)   //thanks to the throws there will be not NullPointer exceptions
    {                                           //thrown if the list is empty

            for(Trait trait:traits)
            {
                addItem(new Object[]{trait},trait);
            }
    }

    public List<Trait> getAllTraits()
    {
        List<Trait> list=new ArrayList<>();
        for(Object itemId: getItemIds())
        {
            list.add((Trait)(getItem(itemId).getItemProperty("Trait").getValue()));
        }
        return list;
    }

//-----------------------------------------------------------------------

    private class TraitWindow extends WindowCrud implements FieldEvents.FocusListener, Window.CloseListener {

        private Trait traitCrud;
        private Object targetId;
        private boolean objCreated;
        private boolean objUpdated;
        private boolean objDeleted;


        public TraitWindow(String caption, FormLayout formLayout) {
            super(caption, formLayout);
            addFocusListener(this);
            addCloseListener(this);
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
        public void buttonClick(Button.ClickEvent event) {

            TextField tf=(TextField)this.formLayout.getComponent(0);

            if(targetId ==null)
            {
                traitCrud= new Trait(tf.getValue(),0);
                addItem(new Object[]{traitCrud},traitCrud);
                objCreated=true;
                objUpdated=false;
                objDeleted=false;
            }
            else
            {
                traitCrud.setName(tf.getValue());
              getItem(targetId).getItemProperty("Trait").setValue(traitCrud);
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
                objDeleted=false;
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
        public void windowClose(CloseEvent e) {
            ((TextField)this.formLayout.getComponent(0)).clear();
            ((TextField)this.formLayout.getComponent(0)).selectAll();
        }
    }

}
