package server;

import requests.*;
import results.*;
import com.google.gson.Gson;
import dataaccess.*;
import service.*;
import spark.*;

import java.util.Objects;

public class Server {
    public static GameDAO gameObject = new GameMemoryDAO();
    public static UserDAO userObject = new UserMemoryDAO();
    public static AuthorizationDAO authorizationObject = new AuthorizationMemoryDAO();

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
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
}
