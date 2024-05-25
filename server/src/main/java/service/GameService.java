package service;

import dataaccess.GameDAO;

public class GameService {
    GameDAO gameObject = new GameDAO();

    public void clear(){
        gameObject.clear();
    }

    //ListGame
    //CreateGame
    //JoinGame
}
