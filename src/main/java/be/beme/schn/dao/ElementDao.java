package be.beme.schn.dao;

import be.beme.schn.narrative.components.Element;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface ElementDao {

    int createElement(int diagramid, String name);

    void setNote(int elementId, String note);

    List<Element> getAllElementsByDiagram(int diagramId);

    void delete(int elementId);


}
