package dataaccess;

import chess.ChessGame;
import model.Game;

import java.util.*;

public class GameDAO {
    Map<Integer, Game> ListOfGames = new Map<>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public Game get(Object key) {
            return null;
        }

        @Override
        public Game put(Integer key, Game value) {
            return null;
        }

        @Override
        public Game remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends Integer, ? extends Game> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<Integer> keySet() {
            return Set.of();
        }

        @Override
        public Collection<Game> values() {
            return List.of();
        }

        @Override
        public Set<Entry<Integer, Game>> entrySet() {
            return Set.of();
        }
    };


    public void createGame(int gameID, String gameName ){
        ListOfGames.put(gameID, new Game(gameID,null,null,gameName,new ChessGame()));
    }

    public Game getGame(int gameID){
        return ListOfGames.get(gameID);
    }

    public Collection<Game> listGames(){
        ArrayList<Game> GameList = new ArrayList<>();
        for(int game : ListOfGames.keySet()){
            GameList.add(ListOfGames.get(game));
        }
        return GameList;
    }

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

    public void updateGame(int gameID, String newGamename){
        Game gameOfInterest = ListOfGames.get(gameID);
        ListOfGames.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), gameOfInterest.whiteUsername(),
                gameOfInterest.blackUsername(), newGamename, gameOfInterest.game()));
    }

    public void updateGame(int gameID, ChessGame newGame){
        Game gameOfInterest = ListOfGames.get(gameID);
        ListOfGames.put(gameOfInterest.gameID(), new Game(gameOfInterest.gameID(), gameOfInterest.whiteUsername(),
                gameOfInterest.blackUsername(), gameOfInterest.gameName(), newGame));

    }

    public void clear(){
        ListOfGames.clear();
    }
}
