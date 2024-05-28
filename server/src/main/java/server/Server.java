package server;

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

        Spark.post("/game",this::joingame);

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
        RegisterResult Result = new RegisterService().register(request);
        if(Objects.equals(Result.getMessage(), "Error: already taken")){
            res.status(403);
        } else if(Objects.equals(Result.getMessage(), "Error: bad request")){
            res.status(400);
        }
        return new Gson().toJson(Result);
    }

    private Object login(Request req, Response res) {
        String body = req.body();
        LoginRequest request = new Gson().fromJson(body,LoginRequest.class);
        LoginResult Result = new LoginService().login(request);
        if(Objects.equals(Result.getMessage(), "Error: unauthorized")){
            res.status(401);
        }
        return new Gson().toJson(Result);
    }

    private Object logout(Request req, Response res) {
        LogoutRequest request = new LogoutRequest(req.headers("authorization"));
        LogoutResult Result = new LogoutService().logout(request);
        if(Objects.equals(Result.getMessage(), "Error: unauthorized")){
            res.status(401);
        }
        return new Gson().toJson(Result);
    }

    private Object listGames(Request req, Response res) {
        String body = req.body();
        ListGamesRequest request = new Gson().fromJson(body,ListGamesRequest.class);
        ListGamesResult Result = new ListGamesService().List(request);
        if(Objects.equals(Result.getMessage(), "Error: unauthorized")){
            res.status(401);
        }
        return new Gson().toJson(Result);
    }

    private Object creategame(Request req, Response res) {
        String body = req.body();
        CreateGameRequest request = new Gson().fromJson(body, CreateGameRequest.class);
        CreateGameResult Result = new CreateGamesService().CreateGame(request);
        if(Objects.equals(Result.getMessage(), "Error: unauthorized")){
            res.status(401);
        } else if(Objects.equals(Result.getMessage(), "Error: bad request")){
            res.status(400);
        }
        return new Gson().toJson(Result);
    }

    private Object joingame(Request req, Response res) {
        String body = req.body();
        JoinGameRequest request = new Gson().fromJson(body, JoinGameRequest.class);
        JoinGameResult Result = new JoinGameService().JoinGame(request);
        if(Objects.equals(Result.getMessage(), "Error: unauthorized")){
            res.status(401);
        } else if(Objects.equals(Result.getMessage(), "Error: already taken")){
            res.status(403);
        } else if(Objects.equals(Result.getMessage(), "Error: bad request")){
            res.status(400);
        }
        return new Gson().toJson(Result);
    }
}
