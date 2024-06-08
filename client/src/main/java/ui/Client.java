package ui;

import chess.ChessGame;
import model.Game;
import results.*;
import serverfacade.ServerFacade;

import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public void run(){
        System.out.println("Welcome to Kendall's CS240 chess project! To get started type 'help'.");

        boolean loggedIn = false;
        ServerFacade serverFacade;
        String authToken = null;
        serverFacade = new ServerFacade(8080);

        while(true){
            String line = readIn();
            
            if(line.equals("help")){

                if (loggedIn){
                    System.out.println("logout-> logs out current user");
                    System.out.println("create game-> prompts user for the name of the game and the creates a game.");
                    System.out.println("list games-> lists all the games that currently exist on the server.");
                    System.out.println("play game-> prompts user for the gameID of the desired game and the desired team color");
                    System.out.println("observe game-> prompts user for the gameID of the desired game and lets the user observe this game.");
                    System.out.println("quit-> exits the program");
                } else {
                    System.out.println("quit-> exits the program");
                    System.out.println("login-> prompts user for login information and logs user in.");
                    System.out.println("register-> prompts user for registration information and registers the user.");
                }
                
            } else if (line.equals("quit")) {
                if(loggedIn){
                    serverFacade.logout(authToken);
                    System.out.println("Logged out.");
                }

                loggedIn = false;
                serverFacade.clear();
                System.out.println("All users and games deleted.");
                System.out.println("Shutting down.");
                break;
                
            } else if (line.equals("login")) {
                System.out.println("Type in your username");
                String username = readIn();

                System.out.println("Type in your password");
                String password = readIn();

                LoginResult result = serverFacade.login(username, password);

                if(result == null){
                    System.out.println("Login unsuccessful. Try again.");
                } else {
                    authToken = result.getAuthToken();
                    loggedIn = true;
                    System.out.println("You are logged in.");
                }
                
            } else if (line.equals("register")) {
                System.out.println("Type in your username");
                String username = readIn();

                System.out.println("Type in your password");
                String password = readIn();

                System.out.println("Type in your email");
                String email = readIn();

                RegisterResult result = serverFacade.register(username, password, email);

                if(result != null){
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration unsuccessful.");
                }
            } else if (line.equals("logout")) {
                LogoutResult result = serverFacade.logout(authToken);

                if(result == null){
                    System.out.println("Unable to logout. If unauthorized error above, you may already be logged out.");
                } else {
                    System.out.println("You are logged out.");
                    loggedIn = false;
                    authToken = null;
                }

            } else if ((line.equals("creategame")) && (loggedIn)) {
                System.out.println("Enter the name of chess game");
                String gameName = readIn();

                System.out.println("Creating game...");
                CreateGameResult result = serverFacade.createGame(gameName,authToken);

                if(result == null){
                    System.out.println("Error creating game. See error messages above.");
                } else {
                    int gameID = (serverFacade.createGame(gameName, authToken)).getGameID();
                    System.out.println("Game created! gameID is: " + gameID);
                }

            } else if ((line.equals("listgames")) && (loggedIn)) {
                ListGamesResult result = serverFacade.listGames(authToken);

                if(result == null){
                    System.out.println("Could not access games. See errors above.");
                } else {
                    System.out.println("List of games:");
                    ArrayList<Game> listGames = (ArrayList<Game>) (serverFacade.listGames(authToken)).getGames();

                    for (var i = 0; i < listGames.size(); i++) {
                        //System.out.printf("%d. %s%n", i+1, listGames.get(i));
                        System.out.println(listGames.get(i));
                    }
                }
            } else if ((line.equals("playgame")) && (loggedIn)) {
                System.out.println("Type in the gameID of the desired game.");
                int gameID = Integer.parseInt(readIn());

                System.out.println("Type in team color you wish to play.");
                String color = readIn();

                ChessGame.TeamColor teamColor = ChessGame.TeamColor.BLACK;
                if(color.equals("white")){
                    teamColor = ChessGame.TeamColor.WHITE;
                } else if (color.equals("black")){
                    teamColor = ChessGame.TeamColor.BLACK;
                }

                JoinGameResult result = serverFacade.joinGame(teamColor,gameID,authToken);

                if(result == null){
                    System.out.println("Unable to join game "+gameID+" as "+color+".");
                } else {
                    System.out.println("Successfully joined game "+gameID+" as "+color+".");
                }

            } else if ((line.equals("observegame")) && (loggedIn)) {
                System.out.println("Type in the gameID of the desired game.");
                int gameID = Integer.parseInt(readIn());

                serverFacade.observeGame(gameID);
            } else {
                System.out.println("Error: Didn't recognize command. Try again.");
            }
            //var words = line.split(" ");
        }
    }

    private String readIn(){
        System.out.printf("%n>>> ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        line = line.toLowerCase();
        line = line.replaceAll("\\s","");

        return line;
    }
}
