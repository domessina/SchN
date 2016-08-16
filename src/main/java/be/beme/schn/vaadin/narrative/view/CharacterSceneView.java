package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.Constants;
import be.beme.schn.ImageUtil;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Trait;
import be.beme.schn.vaadin.crud.CrudListener;
import be.beme.schn.vaadin.narrative.presenter.CharacterScenePresenter;
import be.beme.schn.vaadin.narrative.presenter.NarrativePresenter;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Dotista on 19-04-16.
 */
public class CharacterSceneView extends CustomComponent implements NarrativeView, CrudListener<Trait>, PopupView.PopupVisibilityListener {                      //TODO characterSceneView?

    private HorizontalLayout rootLayout;
    private CharacterScenePresenter presenter;
    private Character character;
    private final File imageFile;
    private File croppedImage;
    private VerticalLayout vLL;
    private VerticalLayout vLM;
    private VerticalLayout vLR;
    private ArrayList<UpDownView> linkviews;
    private ArrayList<Trait> traitsChallenged;
    private ArrayList<Trait> traitsChaltoDelete;
    private ArrayList<Character> linkToDelete;
    private ArrayList<Character>     linkList;
    private int sceneId;
    private TwinColSelect selectTrait;
    private TwinColSelect selectTrait2;


    public CharacterSceneView(Character character,int sceneId) {

        this.character=character;
        this.sceneId=sceneId;
        imageFile = new File(VaadinSession.getCurrent().getAttribute("characterDirectory")+this.character.getPicture());
        linkviews= new ArrayList<>();
        traitsChallenged=new ArrayList<>();
        linkList= new ArrayList<>();
        setStyleName("background-white",true);
        setStyleName("round-corner",true);
        setCompositionRoot(buildContent());
    }

    private Layout buildContent() {
        rootLayout = new HorizontalLayout();
        rootLayout.setWidth(100,Unit.PERCENTAGE);
        rootLayout.setHeightUndefined();
        rootLayout.addComponent(buildLeft());
        rootLayout.addComponent(buildMiddle());
        rootLayout.addComponent(buildRight());
        rootLayout.setExpandRatio(vLL,1);
        rootLayout.setExpandRatio(vLM,2.5F);
        rootLayout.setExpandRatio(vLR,2.5F);
        rootLayout.setSpacing(true);

        return rootLayout;
    }


    private Component buildLeft(){

        Label name = new Label(this.character.getName());

        name.setSizeUndefined();
        vLL= new VerticalLayout();

        writeImageSticker();
        if(croppedImage!=null)
        {
            Image image = new Image();
            Resource resource= new FileResource(croppedImage);
            image.setSource(resource);
            image.setStyleName("circular");
            vLL.addComponent(image);
            vLL.setComponentAlignment(image,Alignment.MIDDLE_CENTER);
        }

        vLL.addComponent(name);
        vLL.setComponentAlignment(name,Alignment.MIDDLE_CENTER);
        vLL.setSizeFull();
        vLL.setSpacing(true);


        return vLL;
    }



    private Component buildMiddle()
    {



        selectTrait = new TwinColSelect();
        selectTrait.setLeftColumnCaption("Characters");
        selectTrait.setRightColumnCaption("To rel");

        selectTrait.setRows(10);
        selectTrait.setMultiSelect(true);//in this case getValue returns a Set

        PopupView pop=new PopupView("Traits challenged",selectTrait);
        pop.setHideOnMouseOut(false);
        pop.addPopupVisibilityListener(event -> {
            if(!event.isPopupVisible())
            {
                vLM.removeAllComponents();
                vLM.addComponent(pop);

                traitsChaltoDelete=new ArrayList<Trait>(traitsChallenged);
                traitsChallenged.clear();

                for(Object o:(Set)selectTrait.getValue())
                {
                    Trait t=(Trait)o;
                   /* Link link= new Link();
                    link.setName(o.toString());*/
                    vLM.addComponent(new UpDownView(t.getName()));
                    updateTraitChallengedList(t);
                }

                createToDeleteTraitChallengedList();
                presenter.setView(this);
                if(!presenter.save()||!presenter.delete())
                {
                    Notification.show(Constants.SYS_ERR,Constants.REPORT_SENT, Notification.Type.ERROR_MESSAGE);
                }
               selectTrait.isVisible();

            }

        });


        vLM = new VerticalLayout();


        vLM.addComponent(pop);
        vLM.setSizeFull();
        vLM.setSpacing(true);
        vLM.setMargin(true);



        return vLM;

    }

    private Component buildRight()
    {


        vLR = new VerticalLayout();
        vLR.setSpacing(true);
        vLR.setSizeFull();
        vLR.setMargin(true);

        selectTrait2 = new TwinColSelect();
        selectTrait2.setLeftColumnCaption("Characters");
        selectTrait2.setRightColumnCaption("To rel");

        selectTrait2.setRows(10);
        selectTrait2.setMultiSelect(true);//in this case getValue returns a Set

//        PopupView pop=new PopupView("Relationship",selectTrait2);
//        pop.setHideOnMouseOut(false);
//        pop.addPopupVisibilityListener(event -> {
//            if(!event.isPopupVisible())
            {
              /*  vLR.removeAllComponents();
                vLR.addComponent(pop);

                linkToDelete=new ArrayList<Character>(linkList);
                linkList.clear();

                for(Object o:(Set)selectTrait2.getValue())
                {
                    Character t=(Character)o;
                   *//* Link link= new Link();
                    link.setName(o.toString());*//*
                    vLR.addComponent(new UpDownView(t.getName()));
                    updateLinkList(t);
                }

                createToDeleteLinkList();
                presenter.setView(this);
                if(!presenter.saveLink()||!presenter.deleteLink())
                {
                    Notification.show(Constants.SYS_ERR,Constants.REPORT_SENT, Notification.Type.ERROR_MESSAGE);
                }
                selectTrait2.isVisible();
*/
            }

//        });


        vLR = new VerticalLayout();


//        vLR.addComponent(pop);
        vLR.setSizeFull();
        vLR.setSpacing(true);
        vLR.setMargin(true);




        return vLR;
    }

        //TODO attention je viens de remarquer que le setId des components est peut etre utilisé qu'en mode debug, lis la javadoc sur cette méthode


    private void writeImageSticker()
    {
        if(this.character.getPicture()!=null)
        {
            croppedImage=new File(VaadinSession.getCurrent().getAttribute("characterDirectory")+"Cropped\\"+this.character.getPicture());
            if((!Files.exists(Paths.get(croppedImage.toURI())))&&this.character.getPicture()!=null)
            {
                try
                {
                    BufferedImage img = ImageIO.read(imageFile);
                    img=ImageUtil.squareCropAndScale(img,100,100);
                    ImageIO.write(img, "png", croppedImage);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }



    }

    //--------Link----------

   /* private void updateLinkList(Character t)
    {
        linkList.add(t);

    }*/
   /* public void createToDeleteLinkList()
    {
        try{
            for(int i=0;i<linkToDelete.size();i++)
            {
               Character t= linkToDelete.get(i);
                for(Character t2:linkList)
                {
                    if(t.getId()==t2.getId())
                    {
                        linkToDelete.set(i,null);               //si pase ici alors trait ne sera pas supprimé
                    }
                }


            }
        }
        catch(IndexOutOfBoundsException e)
        {

            System.out.println("Si vous voyez ça le sprofesseurs c'est que vous avez bien regfardé");
        }


    }*/

    /**The Handler have to be set before*/
    public void loadLinks()
    {
      /*  presenter.setView(this);

        ArrayList<Link> linkList= new ArrayList<>(presenter.getLinksByScAndCh(character.getId(),sceneId));*/
//        selectTrait2.addItems(presenter.characterDao.getAllCharactersByDiagram((int)VaadinSession.getCurrent().getAttribute("diagramId")));


       /* for (Link t:linkList)
        {

            vLR.addComponent(new UpDownView(presenter.characterDao.getCharacterById(t.getToCharacterId()).getName()));

        }
*/






    }
    //-----------------Trait--------------
    private void updateTraitChallengedList(Trait t)
    {
        traitsChallenged.add(t);

    }

    public void createToDeleteTraitChallengedList()
    {
        try{
            for(int i=0;i<traitsChaltoDelete.size();i++)
            {
                Trait t= traitsChaltoDelete.get(i);
                for(Trait t2:traitsChallenged)
                {
                    if(t.getId()==t2.getId())
                    {
                        traitsChaltoDelete.set(i,null);               //si pase ici alors trait ne sera pas supprimé
                    }
                }


            }
        }
        catch(IndexOutOfBoundsException e)
        {

            System.out.println("Si vous voyez ça le sprofesseurs c'est que vous avez bien regfardé");
        }


    }

    /**The Handler have to be set before*/
    public void loadTraits()
    {
        presenter.setView(this);

        traitsChallenged= new ArrayList<>(presenter.getTraitsByScene(this.sceneId));
        selectTrait.addItems(presenter.getTraitsByCharacter(this.character.getId()));

           for(int i=0;i<traitsChallenged.size();i++)
            {
                Trait t=traitsChallenged.get(i);
                if(t.getCharacterId()!=this.character.getId())
                {
                    traitsChallenged.remove(t);
                }
                else
                {
                    vLM.addComponent(new UpDownView(t.getName()));
                }
            }









    }
    //-----------------------------

    private void fillVLM(Collection c)
    {
       /* for()
        Link link= new Link();
        link.setName(o.toString());
        vLM.addComponent(new UpDownView(link));*/
    }


    @Override
    public void setHandler(NarrativePresenter narrativePresenter) {
        this.presenter=(CharacterScenePresenter) narrativePresenter;
    }

    @Override
    public void created(Trait o) {

    }

    @Override
    public void updated(Trait o) {

    }

    @Override
    public void deleted(Trait o) {

    }

    public void setCharacter(Character character)
    {
        this.character=character;
    }

    @Override
    public void popupVisibilityChange(PopupView.PopupVisibilityEvent event) {

    }

    public ArrayList<Trait> getTraitsChallenged() {
        return traitsChallenged;
    }
    public ArrayList<Trait> getTraitsChallengedToDelete() {
        return traitsChaltoDelete;
    }

   /* public ArrayList<Character> getLinkList() {
        return linkList;
    }
    public ArrayList<Character> getLinkToDelete() {
        return linkToDelete;
    }
*/


    public Character getCharacter(){return  this.character;}
    public int getSceneId()
    {
        return  this.sceneId;
    }
}
