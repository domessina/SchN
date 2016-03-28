package be.beme.schn.persistence.daoimpl;

import be.beme.schn.persistence.AbstractPersistenceService;
import be.beme.schn.persistence.dao.UserDao;

/**
 * Created by Dorito on 25-03-16.
 */
public class UserDaoImpl extends AbstractPersistenceService implements UserDao {


    @Override
    public String getPseudo(int userId) {
        return null;
    }

    @Override
    public int getNbDiagrams(int userId) {
        return 0;
    }
}
