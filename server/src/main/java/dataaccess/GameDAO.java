package dataaccess;

import chess.ChessGame;
import model.Game;

import java.util.Collection;

public class GameDAO {
    public Game createGame(int gameID, String gameName ){
        return new Game(gameID,null,null,gameName,new ChessGame());
    }

    /*public Game getGame(int gameID){

    }*/

    /*public Collection<Game> listGames(){

    }*/

    public void updateGame(int gameID, String username, ChessGame.TeamColor teamColor){

    }

    public void updateGame(int gameID, String newGamename){

    }

    public void updateGame(int gameI, ChessGame newGame){

    }

    public void clear(){

    }
}
