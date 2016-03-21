package be.beme.schn.dao;

/**
 * Created by Dorito on 17-03-16.
 */
public interface DiagramDao {




    int createDiagram(int userId, String title);


    String setTitle(int diagramId, String title);

    String setNote(int diagramId, String note);


}
