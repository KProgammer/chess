package dataaccess;

import chess.ChessGame;
import model.Game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface GameDAO extends DAO{
    Map<Integer, Game> LIST_OF_GAMES = new HashMap<>();

    void createGame(Integer gameID, String gameName) throws Exception;

    Game getGame(Integer gameID) throws Exception;

    Collection<Game> listGames() throws Exception;

    void updateGame(Integer gameID, String username, ChessGame.TeamColor teamColor) throws Exception;

    void updateGame(Integer gameID, String newGamename) throws Exception;

    void updateGame(Integer gameID, ChessGame newGame) throws Exception;

    Integer getGameID(String gameName) throws Exception;

    void clear() throws Exception;
}
