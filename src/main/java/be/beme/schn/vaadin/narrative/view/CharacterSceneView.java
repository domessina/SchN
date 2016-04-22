package be.beme.schn.vaadin.narrative.view;

import be.beme.schn.ImageUtil;
import be.beme.schn.narrative.component.*;
import be.beme.schn.narrative.component.Character;
import be.beme.schn.narrative.component.Link;
import be.beme.schn.vaadin.CrudListener;
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
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Dotista on 19-04-16.
 */
public class CharacterSceneView extends CustomComponent implements NarrativeView, CrudListener<Trait> {                      //TODO characterSceneView?

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
    private int sceneId;
    private TwinColSelect selectTrait;


    public CharacterSceneView(Character character,int sceneId) {

        this.character=character;
        this.sceneId=sceneId;
        imageFile = new File(VaadinSession.getCurrent().getAttribute("characterDirectory")+this.character.getPicture());
        linkviews= new ArrayList<>();
        traitsChallenged=new ArrayList<>();
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
        writeImageSticker();
        Image image = new Image();
        Resource resource= new FileResource(croppedImage);
        image.setSource(resource);
        image.setStyleName("circular");

        vLL= new VerticalLayout(image, name);
        vLL.setComponentAlignment(image,Alignment.MIDDLE_CENTER);
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

                for(Object o:(Set)selectTrait.getValue())
                {
                   /* Link link= new Link();
                    link.setName(o.toString());*/
                    vLM.addComponent(new UpDownView(o.toString()));
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

        UpDownView lV =new UpDownView("test");

        vLR = new VerticalLayout(lV,twinColPopUp());
        vLR.setSpacing(true);
        vLR.setSizeFull();
        vLR.setMargin(true);

        return vLR;
    }

    private Component twinColPopUp()                //TODO attention je viens de remarquer que le setId des components est peut etre utilisé qu'en mode debug, lis la javadoc sur cette méthode
    {
        TwinColSelect select = new TwinColSelect();
        select.setLeftColumnCaption("Characters");
        select.setRightColumnCaption("To rel");
        select.addItems("Mercury", "Venus", "Earth", "Mars",
                "Jupiter", "Saturn", "Uranus", "Neptune");

        select.setRows(10);

        PopupView pop=new PopupView("Trait played",select);
        pop.addPopupVisibilityListener(event -> {
            if(!event.isPopupVisible())
            {
                linkviews.clear();
                ArrayList<Object> test= new ArrayList();

                        test.addAll((Set)select.getValue());
                select.isVisible();
            }

        });
        pop.setHideOnMouseOut(false);
        return pop;
    }

    private void writeImageSticker()
    {
        croppedImage=new File(VaadinSession.getCurrent().getAttribute("characterDirectory")+"Cropped\\"+this.character.getPicture());
        if(!Files.exists(Paths.get(croppedImage.toURI())))
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

    /**The Handler have to be set before*/
    public void loadTraits()
    {
        presenter.setView(this);

        traitsChallenged= new ArrayList<>(presenter.getTraitsByScene(this.sceneId));
        selectTrait.addItems(presenter.getTraitsByCharacter(this.character.getId()));


        for (Trait t:traitsChallenged)
        {
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
}
