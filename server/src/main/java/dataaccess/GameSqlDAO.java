package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.Game;

import java.util.ArrayList;
import java.util.Collection;

public class GameSqlDAO implements GameDAO{

    public GameSqlDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void createGame(Integer gameID, String gameName) throws Exception{
        if(gameName == null){
            throw new Exception("gameName was invalid.");
        }

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game (gameID, whiteUsername, blackUsername, gameName, ChessGame) VALUES (?, NULL, NULL,?, ?)")) {
                preparedStatement.setInt(1,gameID);
                preparedStatement.setString(2,gameName);
                preparedStatement.setString(3, new Gson().toJson(new ChessGame()));
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
                        storedGameID = rs.getInt("gameID");
                        whiteUsername = rs.getString("whiteUsername");
                        blackUsername = rs.getString("blackUsername");
                        gameName = rs.getString("gameName");
                        chessGame = new Gson().fromJson(rs.getString("ChessGame"),ChessGame.class);
                    }
                }
            }
        }
        return new Game(storedGameID, whiteUsername,blackUsername,gameName,chessGame);
    }

    @Override
    public Collection<Game> listGames() throws Exception {
        ArrayList<Game> games = new ArrayList<>();

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM game")) {
                try (var rs = preparedStatement.executeQuery()){
                    while (rs.next()){
                        games.add(getGame(rs.getInt("gameID")));
                    }
                }
            }
        }
        return games;
    }

    @Override
    public void updateGame(Integer gameID, String username, ChessGame.TeamColor teamColor) throws Exception{
        if((gameID == null) ||
                (username == null) ||
                (teamColor == null)){
            throw new Exception("Invalid input.");
        }

        String statement = null;
        try (var conn = DatabaseManager.getConnection()) {
            if(teamColor == ChessGame.TeamColor.WHITE){
                statement = "UPDATE game SET whiteUsername = ? WHERE gameID = ?";
            } else{
                statement = "UPDATE game SET blackUsername = ? WHERE gameID = ?";
            }
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2,gameID);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void updateGame(Integer gameID, String newGamename) throws Exception{
        if((gameID == null) ||
                (newGamename == null)){
            throw new Exception("Invalid input.");
        }

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET gameName = ? WHERE gameID = ?")) {
                preparedStatement.setString(1,newGamename);
                preparedStatement.setInt(2,gameID);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void updateGame(Integer gameID, ChessGame newGame) throws Exception{
        if((gameID == null) ||
                (newGame == null)){
            throw new Exception("Invalid input.");
        }

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("UPDATE game SET ChessGame = ? WHERE gameID = ?")) {
                preparedStatement.setString(1,new Gson().toJson(newGame));
                preparedStatement.setInt(2,gameID);
                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public int getGameID(String gameName) throws Exception{
        int gameID = 0;

        if(gameName == null){
            throw new Exception("Invalid input.");
        }

        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID FROM game WHERE gameName = ? LIMIT 1")) {
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

    @Override
    public String[] createStatement(){
        String[] createStatements = {
                """
            CREATE TABLE IF NOT EXISTS  game (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameID` SMALLINT(255),
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256),
              `ChessGame` TEXT,
              PRIMARY KEY (`id`),
              INDEX(gameID),
              INDEX(whiteUsername),
              INDEX(blackUsername),
              INDEX(gameName)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
        };
        return createStatements;
    }
}
