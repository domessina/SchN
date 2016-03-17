package be.beme.schn;

/**
 * Created by Dorito on 17-03-16.
 */
public interface DiagramService {




    int createDiagram(int diagramID, int userID);


    String setTitle(int diagramID, String title);

    String setNote(int diagramID, String note);


}
