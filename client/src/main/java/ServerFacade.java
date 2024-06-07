import com.google.gson.Gson;
import spark.Spark;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class ServerFacade {
    private static URI url;

    public ServerFacade(URI url){
        this.url = url;
    }

    public int run(int desiredPort, String endpoint, URI url) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        /* // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);

        Spark.post("/user",this::register);

        Spark.post("/session",this::login);

        Spark.delete("/session",this::logout);

        Spark.get("/game",this::listGames);

        Spark.post("/game",this::creategame);

        Spark.put("/game",this::joingame);*/

        HttpURLConnection http;
        try {
            // Specify the desired endpoint
            URI uri = null;

            uri = url; //new URI("http://localhost:8080/name");
            //http://localhost:8080/db
            //http://localhost:8080/user
            //http://localhost:8080/session
            //http://localhost:8080/game

            //http = null;
            http = (HttpURLConnection) uri.toURL().openConnection();

            http.setRequestMethod(endpoint);

            // Make the request
            http.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            System.out.println(new Gson().fromJson(inputStreamReader, Map.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Spark.awaitInitialization();
        return Spark.port();
    }

    public void clear(URI url){
        run(0,"DELETE", url);
    }

    public void createGame(URI url){
        run(0,"POST", url);
    }

    public void joinGame(URI url) {
        run(0,"PUT",url);
    }

    public void listGames(URI url){
        run(0,"GET", url);
    }

    public void  login(URI url){
        run(0,"POST", url);
    }

    public void logout(URI url){
        run(0,"DELETE", url);
    }

    public void register(URI url){
        run(0,"P0ST", url);
    }
}
