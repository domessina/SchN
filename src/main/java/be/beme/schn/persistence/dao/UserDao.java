package be.beme.schn.persistence.dao;

import be.beme.schn.persistence.Dao;

/**
 * Created by Dorito on 25-03-16.
 */
public interface UserDao  extends Dao {

    String getPseudo(int userId);

    int getNbDiagrams(int userId);

}
