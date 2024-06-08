package serverfacade;

import chess.ChessGame;
import com.google.gson.Gson;
import requests.LoginRequest;
import requests.RegisterRequest;
import results.*;
import ui.DisplayBoard;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class ServerFacade {
    /*private static URI url;

    public serverfacade.ServerFacade(URI url){
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
            result = null;
            if(e.getMessage().equals("Server returned HTTP response code: 400 for URL: http://localhost:8080/user")||
                    e.getMessage().equals("Server returned HTTP response code: 400 for URL: http://localhost:8080/game")){
                System.out.println("Something was wrong with the http request. Tell Kendall to fix it.");
            } else if(e.getMessage().equals("Server returned HTTP response code: 401 for URL: http://localhost:8080/session") ||
                    e.getMessage().equals("Server returned HTTP response code: 401 for URL: http://localhost:8080/game")) {
                System.out.println("Unauthorized. You must login.");
            } else if(e.getMessage().equals("Server returned HTTP response code: 403 for URL: http://localhost:8080/user") ||
                    e.getMessage().equals("Server returned HTTP response code: 403 for URL: http://localhost:8080/game")){
                System.out.println("Username or game color already taken.");
            } else {
                throw new RuntimeException(e);
            }
        }

        return  result;
    }

    public void clear(URI url){
        var jsonBody = new Gson().toJson(null);
        ClearResult response = new ClearResult(null);
        run("DELETE", url,jsonBody,response, null,null);
    }

    public CreateGameResult createGame(URI url, String gameName, String authToken){
        var jsonBody = new Gson().toJson(gameName);
        CreateGameResult response = new CreateGameResult(null,null);
        return (CreateGameResult) run("POST", url, jsonBody,response,"authorization",authToken);
    }

    public JoinGameResult joinGame(URI url, ChessGame.TeamColor playerColor, Integer gameID, String authToken) {
        var jsonBody = new Gson().toJson(playerColor) + new Gson().toJson(gameID);
        JoinGameResult response = new JoinGameResult(null);
        return (JoinGameResult) run("PUT", url, jsonBody,response,"authorization",authToken);
    }

    public ListGamesResult listGames(URI url, String authToken){
        ListGamesResult response = new ListGamesResult(null,null);
        return (ListGamesResult) run("GET", url, null,response,"authorization",authToken);
    }

    public LoginResult login(URI url, String username, String password){
        var jsonBody = new Gson().toJson(new LoginRequest(username,password));
        LoginResult response = new LoginResult(null,null,null);
        return (LoginResult) run("POST", url, jsonBody,response,null,null);
    }

    public LogoutResult logout(URI url, String authToken){
        LogoutResult response = new LogoutResult(null);
        return (LogoutResult) run("DELETE", url, null,response,"authorization",authToken);
    }

    public  RegisterResult register(URI url, String username, String password, String email){
        var jsonBody = new Gson().toJson(new RegisterRequest(username,password,email));
        RegisterResult response = new RegisterResult(null,null,null);
        return (RegisterResult) run("POST", url, jsonBody,response,null,null);
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
