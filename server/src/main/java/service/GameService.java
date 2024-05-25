package service;

import dataaccess.GameDAO;

import java.util.Collection;

public class GameService {
    GameDAO gameObject = new GameDAO();

    public void clear(){
        gameObject.clear();
    }

    //ListGame
    /*public Collection<String> ListGame(String authToken){

    }*/
    //CreateGame
    //JoinGame
}
