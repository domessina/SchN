package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface ChapterDao extends NarrativeComponentDao {

    void setNote(int chapterId, String note);

    @Override
    Chapter getNComponent(int chapterId);

    List<Chapter> getAllChaptersByPhase(short phase, int diagramId);

    List<Chapter> getAllChaptersByDiagram(int diagramId);

    void setPosition(int pos, int id);
}
