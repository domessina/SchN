package be.beme.schn.persistence.dao;

/**
 * Created by Dorito on 25-03-16.
 */
public interface UserDao {

    String getPseudo(int userId);

    int getNbDiagrams(int userId);

}
