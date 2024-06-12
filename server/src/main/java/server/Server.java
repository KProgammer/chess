package server;

import com.sun.jdi.Value;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static javax.management.remote.JMXConnectorFactory.connect;

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

            String username = authorizationObject.getAuth(command.getAuthString()).username();

            saveSession(session, username);

            switch (command.getCommandType()){
                case CONNECT -> connect(session, username, (ConnectCommand) command);
                case MAKE_MOVE -> makeMove(session, username, (MakeMoveCommand) command);
                case LEAVE -> leaveGame(session, username, (ResignCommand) command);
                case RESIGN -> resign(session, username, (ResignCommand) command);
            }
        } /*catch (UnauthorizedException ex) {
            sendMessage(session.getRemote(), new ErrorMessage("Error: unauthorized"));
        }*/ catch (Exception ex) {
            sendMessage(session.getRemote(), new ErrorMessage("Error: " + ex.getMessage()));
        }
        session.getRemote().sendString("WebSocket response: " + message);
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

    public void saveSession(Session session, String username){
        //gameMap.computeIfAbsent(gameID, k -> new ArrayList<>());
        //gameMap.get(gameID).add(username);
        sessionMap.put(username, session);
    }

    public void connect(Session session, String username, ConnectCommand command) {

    }

    public void makeMove(Session session, String username, MakeMoveCommand command){

    }

    public void leaveGame(Session session, String username, ResignCommand command){

    }

    public void resign(Session session, String username, ResignCommand command){

    }

    public void sendMessage(RemoteEndpoint remoteEndpoint, ErrorMessage errorMessage){

    }
}
