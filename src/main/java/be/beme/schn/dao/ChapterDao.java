package be.beme.schn.dao;

/**
 * Created by Dorito on 21-03-16.
 */
public interface ChapterDao {

    int createChapter(int diagramId, String phase, String title, int previousChapterId);

    String setNote(int chapterId, String note);
}
