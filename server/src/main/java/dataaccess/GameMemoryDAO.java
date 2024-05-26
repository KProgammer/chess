package dataaccess;

import chess.ChessGame;
import model.Game;

import java.util.*;

public class GameMemoryDAO implements GameDAO {

    @Override
    public void createGame(int gameID, String gameName ){
        ListOfGames.put(gameID, new Game(gameID,null,null,gameName,new ChessGame()));
    }

    @Override
    public Game getGame(int gameID){
        return ListOfGames.get(gameID);
    }

    @Override
    public Collection<Game> listGames(){
        ArrayList<Game> GameList = new ArrayList<>();
        for(int game : ListOfGames.keySet()){
            GameList.add(ListOfGames.get(game));
        }
        return GameList;
    }

    @Override
    public void updateGame(int gameID, String username, ChessGame.TeamColor teamColor){
        Game gameOfInterest = ListOfGames.get(gameID);
        if(teamColor == ChessGame.TeamColor.BLACK){
            ListOfGames.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), gameOfInterest.whiteUsername(),
                    username, gameOfInterest.gameName(), gameOfInterest.game()));
        }else {
            ListOfGames.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), username,
                    gameOfInterest.blackUsername(), gameOfInterest.gameName(), gameOfInterest.game()));
        }
    }

    @Override
    public void updateGame(int gameID, String newGamename){
        Game gameOfInterest = ListOfGames.get(gameID);
        ListOfGames.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), gameOfInterest.whiteUsername(),
                gameOfInterest.blackUsername(), newGamename, gameOfInterest.game()));
    }

    @Override
    public void updateGame(int gameID, ChessGame newGame){
        Game gameOfInterest = ListOfGames.get(gameID);
        ListOfGames.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), gameOfInterest.whiteUsername(),
                gameOfInterest.blackUsername(), gameOfInterest.gameName(), newGame));

    }

    /*public void clear(){
        ListOfGames.clear();
    }*/
}
