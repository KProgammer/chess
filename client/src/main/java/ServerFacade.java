import chess.ChessGame;
import com.google.gson.Gson;
import model.Game;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RegisterRequest;
import results.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Collection;

public class ServerFacade {
    /*private static URI url;

    public ServerFacade(URI url){
        this.url = url;
    }*/

    //port included in url
    public Object run(String endpoint, URI url,  String jsonBody, Object response, String key, String value) {
        HttpURLConnection http;
        try {
            // Specify the desired endpoint
            URI uri;
            uri = url;
            http = (HttpURLConnection) uri.toURL().openConnection();
            http.setRequestMethod(endpoint);

            // Specify that we are going to write out data
            http.setDoOutput(true);

            if((key != null) && (value != null)) {
                // Write out a header
                http.addRequestProperty(key, value);
            }

            if(jsonBody != null) {
                // Write out the body
                try (var outputStream = http.getOutputStream()) {
                    outputStream.write(jsonBody.getBytes());
                }
            }

            // Make the request
            http.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Output the response body
        Object result;
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            result = new Gson().fromJson(inputStreamReader, response.getClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  result;
    }

    public void clear(URI url){
        run("DELETE", url,null,null, null,null);
    }

    public Integer createGame(URI url, String gameName, String authToken){
        var jsonBody = new Gson().toJson(gameName);
        CreateGameResult response = null;
        CreateGameResult result = (CreateGameResult) run("POST", url, jsonBody,response,"authorization",authToken);
        return result.getGameID();
    }

    public void joinGame(URI url, ChessGame.TeamColor playerColor, Integer gameID, String authToken) {
        var jsonBody = new Gson().toJson(playerColor) + new Gson().toJson(gameID);
        JoinGameResult response = null;
        run("POST", url, jsonBody,response,"authorization",authToken);
    }

    public Collection<Game> listGames(URI url, String authToken){
        ListGamesResult response = null;
        ListGamesResult result =  (ListGamesResult) run("POST", url, null,response,"authorization",authToken);
        return result.getGames();
    }

    public LoginResult login(URI url, String username, String password){
        var jsonBody = new Gson().toJson(new LoginRequest(username,password));
        LoginResult response = null;
        LoginResult result = (LoginResult) run("POST", url, jsonBody,response,null,null);
        return result;
    }

    public void logout(URI url, String authToken){
        LogoutResult response = null;
        run("POST", url, null,response,"authorization",authToken);
    }

    public  RegisterResult register(URI url, String username, String password, String email){
        var jsonBody = new Gson().toJson(new RegisterRequest(username,password,email));
        RegisterResult response = null;
        RegisterResult result = (RegisterResult) run("POST", url, jsonBody,response,null,null);
        return result;
    }

    /*public RegisterResult register(URI url, String username, String password, String email){
        HttpURLConnection http;
        try {
            // Specify the desired endpoint
            URI uri;
            uri = url;
            http = (HttpURLConnection) uri.toURL().openConnection();
            http.setRequestMethod("POST");

            // Specify that we are going to write out data
            http.setDoOutput(true);

            // Write out the body
            RegisterRequest registerRequest = new RegisterRequest(username,password, email);
            try (var outputStream = http.getOutputStream()) {
                var jsonBody = new Gson().toJson(registerRequest);
                outputStream.write(jsonBody.getBytes());
            }

            // Make the request
            http.connect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RegisterResult registerResult;
        // Output the response body
        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            registerResult = new Gson().fromJson(inputStreamReader, RegisterResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return  registerResult;
    }*/

    public void observeGame(){
        DisplayBoard.main( ChessGame.TeamColor.WHITE,new ChessGame());
        DisplayBoard.main( ChessGame.TeamColor.BLACK,new ChessGame());
    }
}
