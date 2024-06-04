package dataaccess;

import chess.ChessGame;
import model.Game;

import java.util.*;

public class GameMemoryDAO implements GameDAO {

    @Override
    public void createGame(Integer gameID, String gameName ){
        LIST_OF_GAMES.put(gameID, new Game(gameID,null,null,gameName,new ChessGame()));
    }

    @Override
    public Game getGame(int gameID){
        return LIST_OF_GAMES.get(gameID);
    }

    @Override
    public Collection<Game> listGames(){
        ArrayList<Game> games = new ArrayList<>();
        for(int game : LIST_OF_GAMES.keySet()){
            games.add(LIST_OF_GAMES.get(game));
        }
        return games;
    }

    @Override
    public void updateGame(Integer gameID, String username, ChessGame.TeamColor teamColor){
        Game gameOfInterest = LIST_OF_GAMES.get(gameID);
        if(teamColor == ChessGame.TeamColor.BLACK){
            LIST_OF_GAMES.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), gameOfInterest.whiteUsername(),
                    username, gameOfInterest.gameName(), gameOfInterest.game()));
        }else {
            LIST_OF_GAMES.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), username,
                    gameOfInterest.blackUsername(), gameOfInterest.gameName(), gameOfInterest.game()));
        }
    }

    @Override
    public void updateGame(Integer gameID, String newGamename){
        Game gameOfInterest = LIST_OF_GAMES.get(gameID);
        LIST_OF_GAMES.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), gameOfInterest.whiteUsername(),
                gameOfInterest.blackUsername(), newGamename, gameOfInterest.game()));
    }

    @Override
    public void updateGame(Integer gameID, ChessGame newGame){
        Game gameOfInterest = LIST_OF_GAMES.get(gameID);
        LIST_OF_GAMES.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), gameOfInterest.whiteUsername(),
                gameOfInterest.blackUsername(), gameOfInterest.gameName(), newGame));

    }

    @Override
    public int getGameID(String gameName){
        for(int game : LIST_OF_GAMES.keySet()){
            if(Objects.equals(LIST_OF_GAMES.get(game).gameName(), gameName)){
                return LIST_OF_GAMES.get(game).gameID();
            }
        }
        return 0;
    }

    @Override
    public void clear(){
        LIST_OF_GAMES.clear();
    }

    @Override
    public String[] createStatement(){
        return null;
    }
}
