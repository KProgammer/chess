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

    private final int port;

    public ServerFacade(int port){

        this.port = port;
    }
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
            if(e.getMessage().equals("Server returned HTTP response code: 400 for URL: http://localhost:"+port+"/user")||
                    e.getMessage().equals("Server returned HTTP response code: 400 for URL: http://localhost:"+port+"/game")){
                System.out.println("Something was wrong with the http request. Tell Kendall to fix it.");
            } else if(e.getMessage().equals("Server returned HTTP response code: 401 for URL: http://localhost:"+port+"/session") ||
                    e.getMessage().equals("Server returned HTTP response code: 401 for URL: http://localhost:"+port+"/game")) {
                System.out.println("Unauthorized. You must login.");
            } else if(e.getMessage().equals("Server returned HTTP response code: 403 for URL: http://localhost:"+port+"/user") ||
                    e.getMessage().equals("Server returned HTTP response code: 403 for URL: http://localhost:"+port+"/game")){
                System.out.println("Username or game color already taken.");
            } else {
                throw new RuntimeException(e);
            }
        }
        return  result;
    }

    public void clear(){
        var jsonBody = new Gson().toJson(null);
        ClearResult response = new ClearResult(null);
        run("DELETE", URI.create("http://localhost:"+port+"/db"),jsonBody,response, null,null);
    }

    public CreateGameResult createGame(String gameName, String authToken){
        if(gameName == null){
            gameName = "";
        } if(authToken == null){
            authToken = "";
        }
        var jsonBody = new Gson().toJson(new GameName(gameName));
        CreateGameResult response = new CreateGameResult(null,null);
        return (CreateGameResult) run("POST", URI.create("http://localhost:"+port+"/game"), jsonBody,response,"authorization",authToken);
    }

    public JoinGameResult joinGame(ChessGame.TeamColor playerColor, Integer gameID, String authToken) {
        if(authToken == null){
            authToken = "";
        }
        var jsonBody = new Gson().toJson(new JoinGameHelper(playerColor,gameID));
        JoinGameResult response = new JoinGameResult(null);
        return (JoinGameResult) run("PUT", URI.create("http://localhost:"+port+"/game"), jsonBody,response,"authorization",authToken);
    }

    public ListGamesResult listGames(String authToken){
        if(authToken == null){
            authToken = "";
        }
        ListGamesResult response = new ListGamesResult(null,null);
        return (ListGamesResult) run("GET", URI.create("http://localhost:"+port+"/game"), null,response,"authorization",authToken);
    }

    public LoginResult login(String username, String password){
        if(username == null){
            username = "";
        } if (password == null){
            password = "";
        }

        var jsonBody = new Gson().toJson(new LoginRequest(username,password));
        LoginResult response = new LoginResult(null,null,null);
        return (LoginResult) run("POST", URI.create("http://localhost:"+port+"/session"), jsonBody,response,null,null);
    }

    public LogoutResult logout(String authToken){
        if(authToken == null){
            authToken = "";
        }
        LogoutResult response = new LogoutResult(null);
        return (LogoutResult) run("DELETE", URI.create("http://localhost:"+port+"/session"), null,response,"authorization",authToken);
    }

    public  RegisterResult register(String username, String password, String email){
                var jsonBody = new Gson().toJson(new RegisterRequest(username,password,email));
        RegisterResult response = new RegisterResult(null,null,null);
        return (RegisterResult) run("POST", URI.create("http://localhost:"+port+"/user"), jsonBody,response,null,null);
    }

    public Boolean observeGame(Integer gameID){
        if((gameID == null) ||
                ((gameID < 999) || (gameID > 10000))){
            System.out.println("Not a valid gameID.");
            return false;
        } else {
            DisplayBoard.main(ChessGame.TeamColor.WHITE, new ChessGame());
            DisplayBoard.main(ChessGame.TeamColor.BLACK, new ChessGame());
            return true;
        }
    }
}
