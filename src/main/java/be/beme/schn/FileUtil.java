package be.beme.schn;

import be.beme.schn.narrative.component.Diagram;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Dotista on 21-08-16.
 */
public final class FileUtil  {

    private FileUtil(){}

    public static void deleteDiagramTree(int userId, int diagramId){

        File file = new File(Constants.BASE_DIR+"Users\\"+userId+"\\Diagrams\\"+diagramId);
        FileUtils.deleteQuietly(file);
    }



    public static void createDiagramTree(int userId, int diagramId){
        String baseDiagram=Constants.BASE_DIR+"Users\\"+userId+"\\Diagrams\\"+diagramId;
        File[] tree = new File[5];
        tree[0]= new File(baseDiagram);
        tree[1]= new File(baseDiagram+"\\Chapters");
        tree[2]= new File(baseDiagram+"\\Characters");
        tree[3]= new File(baseDiagram+"\\Characters"+"\\Cropped");
        tree[4]= new File(baseDiagram+"\\Scenes");

        try{
            for(int i=0;i<5;i++){
                FileUtils.forceMkdir(tree[i]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static File getDiagramPicture(Diagram d){
        return FileUtils.getFile(Constants.BASE_DIR+"Users\\"+d.getUserId()+"\\Diagrams\\"+d.getId()+"\\"+d.getPictureId());
    }


    //must to be done by the social platform
    public static void createUserTree(int userId){
      /*  File file = new File(Constants.BASE_DIR+"Users\\"+userId);
        try {
            org.apache.commons.io.FileUtils.forceMkdir(file);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
