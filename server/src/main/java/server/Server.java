package server;

import com.google.gson.Gson;
import dataaccess.AuthorizationDAO;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import model.User;
import service.AuthorizationService;
import service.GameService;
import service.UserService;
import spark.*;

import java.util.Map;
import java.util.Objects;

public class Server {
    GameDAO gameObject = new GameDAO();
    UserDAO userObject = new UserDAO();
    AuthorizationDAO authorizationObject = new AuthorizationDAO();
    GameService gameService = new GameService(gameObject,authorizationObject);
    UserService userService = new UserService(userObject,authorizationObject);
    AuthorizationService authorizationService = new AuthorizationService(authorizationObject,userObject);

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);

        Spark.post("/game", this::listGames);

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

    private Objects clear(Request req, Response res) {
        authorizationService.clear();
        gameService.clear();
        userService.clear();
        return null;
    }

    private Objects register(Request req, Response res) {
        userService.register(req.body(), req.body(), req.body());
        return null;
    }

    private Objects login(Request req, Response res) {
        authorizationService.login(req.body(),req.body());
        return null;
    }

    private Objects logout(Request req, Response res) {
        authorizationService.logout(req.body());
        return null;
    }

    private Objects listGames(Request req, Response res) {
        gameService.List(req.body());
        return null;
    }

    private Objects creategame(Request req, Response res) {
        gameService.CreateGame(req.body(), req.body());
        return null;
    }

    private Objects joingame(Request req, Response res) {
        //gameService.JoinGame(req.body(), req.body(), req.body());
        return null;
    }
}
