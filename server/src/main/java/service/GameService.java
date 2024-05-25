package service;

import chess.ChessGame;
import dataaccess.AuthorizationDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

public class GameService {
    private final GameDAO gameObject;
    private final AuthorizationDAO authorizationObject;
    //GameDAO gameObject = new GameDAO();
    //AuthorizationDAO authorizationObject = new AuthorizationDAO();

    public GameService(GameDAO gameObject, AuthorizationDAO authorizationObject){

        this.gameObject = gameObject;
        this.authorizationObject = authorizationObject;
    }

    public void clear(){
        gameObject.clear();
    }

    //ListGame
    public Collection<String> List(String authToken){
        ArrayList<String> result = new ArrayList<>();

        if (authorizationObject.getAuth(authToken) == null){
            result.add("'message':'Error: unauthorized'");
            return result;
        }

        return gameObject.listGames();
    }

    //CreateGame
    public Collection<String> CreateGame(String authToken, String gameName){
        ArrayList<String> result = new ArrayList<>();

        if (authorizationObject.getAuth(authToken) == null){
            result.add("'message':'Error: unauthorized'");
            return result;
        }

        int gameID = createGameID();
        gameObject.createGame(gameID,gameName);

        result.add("'gameID':"+gameID);

        return result;
    }

    private int createGameID(){
        Random rand = new Random();
        int min = 1000;
        int max = 10000;
        int gameID = (rand.nextInt(max-min+1)+min);

        //Make sure that the authToken is unique.
        while(gameObject.getGame(gameID) != null) {
            gameID = (rand.nextInt(max-min+1)+min);
        }

        return gameID;
    }

    //JoinGame
    public Collection<String> JoinGame(String authToken, ChessGame.TeamColor teamColor, int gameID){
        ArrayList<String> result = new ArrayList<>();

        if (authorizationObject.getAuth(authToken) == null){
            result.add("'message':'Error: unauthorized'");
            return result;
        }

        if((teamColor.equals(ChessGame.TeamColor.BLACK) &&
                gameObject.getGame(gameID).blackUsername() != null) ||
                ((teamColor.equals(ChessGame.TeamColor.WHITE) &&
                        gameObject.getGame(gameID).whiteUsername() != null))){
            result.add("'message':'Error: already taken'");
            return result;
        }

        gameObject.updateGame(gameID,authorizationObject.getAuth(authToken),teamColor);

        return result;
    }
}
