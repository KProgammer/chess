package service;

import dataaccess.AuthorizationDAO;

import static dataaccess.AuthorizationDAO.ListAuthtokenUser;
import static dataaccess.GameDAO.ListOfGames;
import static dataaccess.UserDAO.ListOfUsers;

public class ClearService {
    public ClearResult clear(){
        ListAuthtokenUser.clear();
        ListOfGames.clear();
        ListOfUsers.clear();

        return new ClearResult(null);
    }
}
