package dataaccess;

import chess.ChessGame;
import model.Game;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameSqlDAO implements GameDAO{
    Map<Integer, Game> LIST_OF_GAMES = new HashMap<>();

    public GameSqlDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void createGame(int gameID, String gameName) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, ChessGame) VALUES (?, NULL, NULL,?, ?)")) {
                preparedStatement.setInt(1,gameID);
                preparedStatement.setString(2,gameName);
                preparedStatement.setObject(3,new ChessGame() );
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public Game getGame(int gameID) throws Exception{
        int storedGameID = 0;
        String whiteUsername = null;
        String blackUsername = null;
        String gameName = null;
        ChessGame chessGame = null;

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID, whiteUsername, blackUsername, gameName, ChessGame FROM game WHERE gameID = ? LIMIT 1")) {
                preparedStatement.setInt(1, gameID);
                try (var rs = preparedStatement.executeQuery()){
                    while(rs.next()) {
                        whiteUsername = rs.getString("whiteUsername");
                        blackUsername = rs.getString("blackUsername");
                        gameName = rs.getString("gameName");
                        chessGame = (ChessGame) rs.getObject("ChessGame");
                    }
                }
            }
        }
        return new Game(gameID, whiteUsername,blackUsername,gameName,chessGame);
    }

    @Override
    public Collection<Game> listGames() throws Exception {
        ArrayList<Game> games = new ArrayList<>();
        int gameID = 0;
        String whiteUsername = null;
        String blackUsername = null;
        String gameName = null;
        ChessGame chessGame = null;

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM authorization")) {
                try (var rs = preparedStatement.executeQuery()){
                    while (rs.next()){
                        gameID = rs.getInt("gameID");
                        whiteUsername = rs.getString("whiteUsername");
                        blackUsername = rs.getString("blackUsername");
                        gameName = rs.getString("gameName");
                        chessGame = (ChessGame) rs.getObject("ChessGame");
                        games.add(new Game(gameID,whiteUsername,blackUsername,gameName,chessGame));
                    }
                }
            }
        }
        return games;
    }

    @Override
    public void updateGame(int gameID, String username, ChessGame.TeamColor teamColor) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET teamColor = ? WHERE gameID = ?")) {
                preparedStatement.setObject(1, teamColor);
                preparedStatement.setInt(2,gameID);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void updateGame(int gameID, String newGamename) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET gameName = ? WHERE gameID = ?")) {
                preparedStatement.setString(1,newGamename);
                preparedStatement.setInt(2,gameID);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void updateGame(int gameID, ChessGame newGame) throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET ChessGame = ? WHERE gameID = ?")) {
                preparedStatement.setObject(1,newGame);
                preparedStatement.setInt(2,gameID);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public int getGameID(String gameName) throws Exception{
        int gameID = 0;

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID FROM authorization WHERE" +
                    "gameName = '"+gameName+"' LIMIT 1")) {
                preparedStatement.setString(1,gameName);
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()){
                        gameID = rs.getInt("gameID");
                    }
                }
            }
        }
        return gameID;
    }

    @Override
    public void clear() throws Exception{
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM game")) {
                preparedStatement.executeUpdate();
            }
        }
    }

    private final String[] createStatements = {
            //`json` TEXT DEFAULT NULL,
            """
            CREATE TABLE IF NOT EXISTS  game (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameID` SMALLINT(255),
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `ChessGame` varchar(256),
              PRIMARY KEY (`id`),
              INDEX(gameID),
              INDEX(whiteUsername),
              INDEX(blackUsername),
              INDEX(gameName),
              INDEX(ChessGame)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DROP TABLE authorization")) {
                preparedStatement.executeUpdate();
            }
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(ex.getMessage());
        }
    }
}
