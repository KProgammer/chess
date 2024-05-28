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
        ArrayList<Game> games = new ArrayList<>();
        for(int game : ListOfGames.keySet()){
            games.add(ListOfGames.get(game));
        }
        return games;
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

    @Override
    public int getGameID(String gameName){
        for(int game : ListOfGames.keySet()){
            if(Objects.equals(ListOfGames.get(game).gameName(), gameName)){
                return ListOfGames.get(game).gameID();
            }
        }
        return 0;
    }

    @Override
    public void clear(){
        ListOfGames.clear();
    }
}
