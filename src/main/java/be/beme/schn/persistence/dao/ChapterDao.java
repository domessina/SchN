package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Chapter;
import be.beme.schn.persistence.Dao;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface ChapterDao extends Dao {

    int create(Chapter chapter);

    void setNote(int chapterId, String note);

    void update(Chapter chapter);

    Chapter getChapterById(int chapterId);


    List<Chapter> getAllChaptersByPhase(short phase, int diagramId);

    List<Chapter> getAllChaptersByDiagram(int diagramId);

    void setPosition(int pos, int id);

    void delete(int chapterId);
}
