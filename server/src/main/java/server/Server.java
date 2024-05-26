package server;

import com.google.gson.Gson;
import dataaccess.*;
import service.*;
import spark.*;

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
        return new Gson().toJson(new RegisterService().register(request));
    }

    private Object login(Request req, Response res) {
        String body = req.body();
        LoginRequest request = new Gson().fromJson(body,LoginRequest.class);
        return new Gson().toJson(new LoginService().login(request));
    }

    private Object logout(Request req, Response res) {
        String body = req.body();
        LogoutRequest request = new Gson().fromJson(body,LogoutRequest.class);
        return new Gson().toJson(new LogoutService().logout(request));
    }

    private Object listGames(Request req, Response res) {
        String body = req.body();
        ListGamesRequest request = new Gson().fromJson(body,ListGamesRequest.class);
        return new Gson().toJson(new ListGamesService().List(request));
    }

    private Object creategame(Request req, Response res) {
        String body = req.body();
        CreateGameRequest request = new Gson().fromJson(body, CreateGameRequest.class);
        return new Gson().toJson(new CreateGamesService().CreateGame(request));
    }

    private Object joingame(Request req, Response res) {
        String body = req.body();
        JoinGameRequest request = new Gson().fromJson(body, JoinGameRequest.class);
        return new Gson().toJson(new JoinGameService().JoinGame(request));
    }
}
