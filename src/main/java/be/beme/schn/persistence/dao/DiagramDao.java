package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Diagram;

import java.util.List;

/**
 * Created by Dorito on 17-03-16.
 */
public interface DiagramDao  extends NarrativeComponentDao {


    @Override
    Diagram getNComponent(int id);

    List<Diagram> getAllDiagramsByUser(int userId);

    Diagram getDiagramById( int diagramId);

    Diagram getDiagramByClientId(int diagramIdClient);

    void setDiagramEnabled(int diagramId,boolean isEnabled);

    int getIdFromClientId(int clientId);

}
