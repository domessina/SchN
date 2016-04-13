package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Chapter;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface ChapterDao {

    int create(int diagramId, short phase, String title, short place, String note);

    void setNote(int chapterId, String note);

    void update(Object[] args);

    Chapter getChapterById(int chapterId);


    List<Chapter> getAllChaptersByPhase(short phase, int diagramId);

    List<Chapter> getAllChaptersByDiagram(int diagramId);

    void delete(int chapterId);
}
