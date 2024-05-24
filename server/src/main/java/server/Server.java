package server;

import com.google.gson.Gson;
import model.User;
import service.AuthorizationService;
import service.GameService;
import service.UserService;
import spark.*;

import java.util.Map;
import java.util.Objects;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::delete);

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

    private Objects delete(Request req, Response res) {
        //AuthorizationService
        return null;
    }

    private Objects register(Request req, Response res) {
        //UserService
        return null;
    }

    private Objects login(Request req, Response res) {
        //UserService
        return null;
    }

    private Objects logout(Request req, Response res) {
        //UserService
        return null;
    }

    private Objects listGames(Request req, Response res) {
        //GameService
        return null;
    }

    private Objects creategame(Request req, Response res) {
        //GameService
        return null;
    }

    private Objects joingame(Request req, Response res) {
        //GameService
        return null;
    }

    /*private Object listNames(Request req, Response res) {
        res.type("application/json");
        return new Gson().toJson(Map.of("name", games));
    }*/

}
