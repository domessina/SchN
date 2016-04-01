package be.beme.schn.persistence.daoimpl.test;

import be.beme.schn.persistence.dao.DiagramDao;
import be.beme.schn.narrative.component.Diagram;

import java.util.List;

/**
 * Created by Dorito on 25-03-16.
 */
public class DiagramDaoImplTest implements DiagramDao {
    @Override
    public int createDiagram(int userId, String title) {
        return 0;
    }

    @Override
    public String setTitle(int diagramId, String title) {
        return null;
    }



    @Override
    public Integer[] getAllDiagramsIdByUser(int userId) {
        return new Integer[0];
    }

    @Override
    public List<Diagram> getAllDiagramsByUser(int userId) {
        return null;
    }

    @Override
    public Diagram getDiagramById(int diagramId) {
        return null;
    }

    @Override
    public void deleteDiagram(int diagramId) {

    }

    @Override
    public String getTitle(int diagramId) {
        return "Titre";
    }
}
