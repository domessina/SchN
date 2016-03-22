package be.beme.schn.dao;

import be.beme.schn.narrative.components.Diagram;

import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
public interface DiagramDao {




    int createDiagram(int userId, String title);

    String setTitle(int diagramId, String title);

    Integer[] getAllDiagramsIdByUser(int userId);

    List<Diagram> getAllDiagramsByUser(int userId);

    Diagram getDiagramById( int diagramId);

    void deleteDiagram(int diagramId);

}
