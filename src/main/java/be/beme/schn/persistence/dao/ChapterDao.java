package be.beme.schn.persistence.dao;

import be.beme.schn.narrative.component.Chapter;

import java.util.List;

/**
 * Created by Dorito on 21-03-16.
 */
public interface ChapterDao {

    int createChapter(int diagramId, String phase, String title, int previousChapterId);

    void setNote(int chapterId, String note);

    Chapter getChapterById(int chapterId);

    List<Chapter> getAllChaptersByDiagram(int diagramId);

    void delete(int chapterId);
}
