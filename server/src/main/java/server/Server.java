package server;

import chess.ChessGame;
import model.Game;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import requests.*;
import results.*;
import com.google.gson.Gson;
import dataaccess.*;
import service.*;
import spark.*;
import websocket.commands.ConnectCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.ResignCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.management.remote.JMXConnectorFactory.connect;
@WebSocket
public class Server {
    public static GameDAO gameObject;
    public static UserDAO userObject;
    public static AuthorizationDAO authorizationObject;
    public static Map<Integer, ArrayList<String>> gameMap = new HashMap<>();
    public static Map<String, Session> sessionMap = new HashMap<>();

    static {
        try {
            gameObject = new GameSqlDAO();
            userObject = new UserSqlDAO();
            authorizationObject = new AuthorizationSqlDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.webSocket("/ws", Server.class); //This has to be first

        Spark.delete("/db", this::clear);

        Spark.post("/user",this::register);

        Spark.post("/session",this::login);

        Spark.delete("/session",this::logout);

        Spark.get("/game",this::listGames);

        Spark.post("/game",this::creategame);

        Spark.put("/game",this::joingame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        try{
            UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

            //String username = authorizationObject.getAuth(command.getAuthString()).username();
            String authToken = command.getAuthString();

            saveSession(session, authToken);

            switch (command.getCommandType()){
                case CONNECT -> connect(session, authToken, (ConnectCommand) command);
                case MAKE_MOVE -> makeMove(session, authToken, (MakeMoveCommand) command);
                case LEAVE -> leaveGame(session, authToken, (ResignCommand) command);
                case RESIGN -> resign(session, authToken, (ResignCommand) command);
            }
        } catch (UnauthorizedException ex) {
            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
        } catch (Exception ex) {
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object clear(Request req, Response res) {
        return new Gson().toJson(new ClearService().clear());
    }

    private Object register(Request req, Response res) {
        String body = req.body();
        RegisterRequest request = new Gson().fromJson(body,RegisterRequest.class);
        RegisterResult register = new RegisterService().register(request);
        if(Objects.equals(register.getMessage(), "Error: already taken")){
            res.status(403);
        } else if(Objects.equals(register.getMessage(), "Error: bad request")){
            res.status(400);
        }
        return new Gson().toJson(register);
    }

    private Object login(Request req, Response res) {
        String body = req.body();
        LoginRequest request = new Gson().fromJson(body,LoginRequest.class);
        LoginResult result = new LoginService().login(request);
        if(Objects.equals(result.getMessage(), "Error: unauthorized")){
            res.status(401);
        }
        return new Gson().toJson(result);
    }

    private Object logout(Request req, Response res) {
        LogoutRequest request = new LogoutRequest(req.headers("authorization"));
        LogoutResult result = new LogoutService().logout(request);
        if(Objects.equals(result.getMessage(), "Error: unauthorized")){
            res.status(401);
        }
        return new Gson().toJson(result);
    }

    private Object listGames(Request req, Response res) {
        ListGamesRequest request = new ListGamesRequest(req.headers("authorization"));
        ListGamesResult result = new ListGamesService().makeList(request);
        if(Objects.equals(result.getMessage(), "Error: unauthorized")){
            res.status(401);
        }
        return new Gson().toJson(result);
    }

    private Object creategame(Request req, Response res) {
        String body = req.body();
        CreateGameRequest request = new Gson().fromJson(body, CreateGameRequest.class);
        request.setAuthToken(req.headers("authorization"));
        CreateGameResult result = new CreateGamesService().createGame(request);
        if(Objects.equals(result.getMessage(), "Error: unauthorized")){
            res.status(401);
        } else if(Objects.equals(result.getMessage(), "Error: bad request")){
            res.status(400);
        }
        return new Gson().toJson(result);
    }

    private Object joingame(Request req, Response res) {
        String body = req.body();
        JoinGameRequest request = new Gson().fromJson(body, JoinGameRequest.class);
        request.setAuthToken(req.headers("authorization"));
        JoinGameResult result = new JoinGameService().joinGame(request);
        if(Objects.equals(result.getMessage(), "Error: unauthorized")){
            res.status(401);
        } else if(Objects.equals(result.getMessage(), "Error: already taken")){
            res.status(403);
        } else if(Objects.equals(result.getMessage(), "Error: bad request")){
            res.status(400);
        }
        return new Gson().toJson(result);
    }

    public void saveSession(Session session, String authToken) throws UnauthorizedException {
        if (authorized(authToken)) {
            sessionMap.put(authToken, session);
        }
    }

    public void connect(Session session, String authToken, ConnectCommand command) {
        if(authorized(authToken)) {
            String message = "";
            Game gameOfInterest = null;

            try {
                gameOfInterest = gameObject.getGame(command.getGameID());
            } catch (Exception ex) {
                sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
            }

            if (gameOfInterest == null) {
                message = "Error: Game doesn't exist.";
                sendMessage(session.getRemote(), new ErrorMessage(message));
            } else {
                gameMap.computeIfAbsent(command.getGameID(), k -> new ArrayList<>());
                gameMap.get(command.getGameID()).add(authToken);

                try {
                    if (authorizationObject.getAuth(authToken).username().equals(gameOfInterest.whiteUsername())) {
                        message = authorizationObject.getAuth(authToken).username() + "has joined as the white team.";
                    } else if (authorizationObject.getAuth(authToken).username().equals(gameOfInterest.blackUsername())) {
                        message = authorizationObject.getAuth(authToken).username() + "has joined as the black team.";
                    } else {
                        message = authorizationObject.getAuth(authToken).username() + "has joined as an observer.";
                    }
                } catch (Exception ex) {
                    sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
                }
                sendMessage(session.getRemote(), new NotificationMessage(message));
            }
        }
    }

    public void makeMove(Session session, String authToken, MakeMoveCommand command){
        if(authorized(authToken)) {
            ArrayList<String> users = gameMap.get(command.getGameID());
            session = sessionMap.get(authToken);

            boolean whiteHasWon = false;
            boolean blackHasWon = false;

            String username = null;
            try {
                username = authorizationObject.getAuth(authToken).username();
                gameObject.getGame(command.getGameID()).game().makeMove(command.getChessMove());

                for (String user : users) {
                    session = sessionMap.get(user);
                    sendMessage(session.getRemote(), new NotificationMessage(username + "has moved from " +
                            command.getChessMove().getStartPosition() +
                            " to " + command.getChessMove().getEndPosition()));
                }

                ChessGame.TeamColor teamColor = null;
                if(gameObject.getGame(command.getGameID()).blackUsername().equals(username)){
                    teamColor = ChessGame.TeamColor.BLACK;
                } else {
                    teamColor = ChessGame.TeamColor.WHITE;
                }

                if(gameObject.getGame(command.getGameID()).game().isInCheckmate(teamColor)){
                    sendMessageToAll(users,username+"is in checkmate.");
                } else if (!gameObject.getGame(command.getGameID()).game().isInCheckmate(teamColor)) {
                    sendMessageToAll(users, username+"has won.");
                } else if (gameObject.getGame(command.getGameID()).game().isInCheck(teamColor)) {
                    sendMessageToAll(users, username+"is in check.");
                } else {
                    for (String user : users) {
                        session = sessionMap.get(user);
                        sendMessage(session.getRemote(), new LoadGameMessage(command.getGameID(),
                                gameObject.getGame(command.getGameID()).game(), blackHasWon, whiteHasWon, teamColor));
                    }
                }

            } catch (Exception ex) {
                sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
            }


        }
    }

    public void leaveGame(Session session, String authToken, ResignCommand command){
        if(authorized(authToken)) {
            sessionMap.remove(authToken);
            ArrayList<String> users = gameMap.get(command.getGameID());

            String username = "";

            try {
                username = authorizationObject.getAuth(authToken).username();
            } catch (Exception ex) {
                sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
            }

            for (String user : users) {
                session = sessionMap.get(user);
                sendMessage(session.getRemote(), new NotificationMessage(username + "has left the game."));
            }
        }

    }

    public void resign(Session session, String authToken, ResignCommand command){
        if(authorized(authToken)) {
            sessionMap.remove(authToken);
            ArrayList<String> users = gameMap.get(command.getGameID());

            for (String user : users) {
                session = sessionMap.get(user);
                try {
                    sendMessage(session.getRemote(),
                            new NotificationMessage(authorizationObject.getAuth(authToken).username() + "has forfeit the game."));
                } catch (Exception ex) {
                    sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
                }
            }

            gameMap.remove(command.getGameID());
        }
    }

    public void sendMessage(RemoteEndpoint remoteEndpoint, Object message){
        try {
            remoteEndpoint.sendString(new Gson().toJson(message));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageToAll(ArrayList<String> users, String message){
        Session session;
        for (String user : users) {
            session = sessionMap.get(user);
            sendMessage(session.getRemote(), new NotificationMessage(message));
        }
    }

    private class UnauthorizedException extends Exception {
        private UnauthorizedException(){
            super();
        }
    }

    private boolean authorized(String authToken){

        try {
            if(authorizationObject.getAuth(authToken) == null ||
                    authorizationObject.getAuth(authToken).username() == null ||
                    authorizationObject.getAuth(authToken).authToken() == null){
                throw new UnauthorizedException();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
