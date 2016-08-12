package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Diagram;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
public interface DiagramDao  extends Dao {




    int createDiagram(int userId, String title);

    String setTitle(int diagramId, String title);

    Integer[] getAllDiagramsIdByUser(int userId);

    List<Diagram> getAllDiagramsByUser(int userId);

    Diagram getDiagramById( int diagramId);

    void deleteDiagram(int diagramId);

    String getTitle(int diagramId);

}