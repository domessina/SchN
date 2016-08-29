package be.beme.schn.persistence.dao.synch;

import be.beme.schn.narrative.component.Diagram;

import java.util.List;

/**
 * Created by Dorito on 18-08-16.
 */
public interface DiagramSynchDao  {

    void setIdClientServer(int diagramIdClient, int diagramIdServer);
    List<Diagram> newFromServer(int userId);

    boolean isDiagramToSynch(int diagramIdClient);

    String getServerAction(int diagramIdClient);

    void setLastSelectedAction(String clientAction);
}
