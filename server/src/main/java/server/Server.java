package server;

import com.google.gson.Gson;
import dataaccess.*;
import model.User;
import service.*;
import spark.*;

public class Server {
    public static GameDAO gameObject = new GameMemoryDAO();
    UserDAO userObject = new UserMemoryDAO();
    public static AuthorizationDAO authorizationObject = new AuthorizationMemoryDAO();
    /*GameService gameService = new GameService(gameObject,authorizationObject);
    UserService userService = new UserService(userObject,authorizationObject);
    AuthorizationService authorizationService = new AuthorizationService(authorizationObject,userObject);*/

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);

        //Spark.post("/user",this::register);

        //Spark.post("/session",this::login);

        //Spark.delete("/session",this::logout);

        //Spark.get("/game",this::listGames);

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

    /*private Object register(Request req, Response res) {
        String body = req.body();
        User gs = new Gson().fromJson(body,User.class);

        return new Gson().toJson(userService.register(gs.username(), req.params(":password"), req.params(":email")));
    }*/

    /*private Object login(Request req, Response res) {
        return new Gson().toJson(authorizationService.login(req.params(":username"),req.params(":password")));
    }*/

    /*private Object logout(Request req, Response res) {
        return new Gson().toJson(authorizationService.logout(req.params(":authToken")));
    }*/

    /*private Object listGames(Request req, Response res) {
        return new Gson().toJson(gameService.List(req.params(":authToken")));
    }*/

    private Object creategame(Request req, Response res) {
        String body = req.body();
        CreateGameRequest request = new Gson().fromJson(body, CreateGameRequest.class);
        return new Gson().toJson(new CreateGamesService(request));
    }

    private Object joingame(Request req, Response res) {
        //return new Gson().toJson(gameService.JoinGame(req.params("authToken"), req.params(":teamColor"), req.params(":gameID")));
        return null;
    }
}
