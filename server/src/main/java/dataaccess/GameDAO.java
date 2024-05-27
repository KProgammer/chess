package dataaccess;

import chess.ChessGame;
import model.Game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface GameDAO {
    Map<Integer, Game> ListOfGames = new HashMap<>();

    void createGame(int gameID, String gameName);

    Game getGame(int gameID);

    Collection<Game> listGames();

    void updateGame(int gameID, String username, ChessGame.TeamColor teamColor);

    void updateGame(int gameID, String newGamename);

    void updateGame(int gameID, ChessGame newGame);

    int getGameID(String gameName);

    void clear();
}
