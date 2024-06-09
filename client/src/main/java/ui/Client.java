package ui;

import chess.ChessGame;
import model.Game;
import results.*;
import serverfacade.ServerFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private static boolean loggedIn = false;
    private static final ServerFacade SERVER_FACADE = new ServerFacade(8080);
    private static String authToken = null;
    private static Map<Integer, Game> gameMap = new HashMap<Integer, Game>();

    public void run(){
        System.out.println("Welcome to Kendall's CS240 chess project! To get started type 'help'.");

        while(true){
            String line = readIn();
            
            if(line.equals("help")){
                helpCommand();
                
            } else if (line.equals("quit") && !loggedIn) {
                quitCommand();
                break;
                
            } else if (line.equals("login") && !loggedIn) {
                loginCommand();

            } else if (line.equals("register") && !loggedIn) {
                registerCommand();

            } else if (line.equals("logout") && loggedIn) {
                logoutCommand();

            } else if ((line.equals("creategame")) && (loggedIn)) {
                createGameCommand();

            } else if ((line.equals("listgames")) && (loggedIn)) {
                listGamesCommand();

            } else if ((line.equals("playgame")) && (loggedIn)) {
                playGamesCommand();

            } else if ((line.equals("observegame")) && (loggedIn)) {
                observeGameCommand();
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

    private void helpCommand(){
        if (loggedIn){
            loggedInPrompts();
        } else {
            loggedOutPrompts();
        }
    }

    private void loggedInPrompts(){
        System.out.println("logout-> logs out current user");
        System.out.println("create game-> prompts user for the name of the game and the creates a game.");
        System.out.println("list games-> lists all the games that currently exist on the server.");
        System.out.println("play game-> prompts user for the gameID of the desired game and the desired team color");
        System.out.println("observe game-> prompts user for the gameID of the desired game and lets the user observe this game.");
    }

    private void loggedOutPrompts(){
        System.out.println("quit-> exits the program");
        System.out.println("login-> prompts user for login information and logs user in.");
        System.out.println("register-> prompts user for registration information and registers the user.");
    }

    private void quitCommand(){
        loggedIn = false;
        SERVER_FACADE.clear();
        System.out.println("All users and games deleted.");
        System.out.println("Shutting down.");
    }

    private void loginCommand(){
        System.out.println("Type in your username");
        String username = readIn();

        System.out.println("Type in your password");
        String password = readIn();

        LoginResult result = SERVER_FACADE.login(username, password);

        if(result == null){
            System.out.println("Login unsuccessful. Try again.");
        } else {
            authToken = result.getAuthToken();
            loggedIn = true;
            System.out.println("You are logged in.");
        }
    }

    private void registerCommand(){
        System.out.println("Type in your username");
        String username = readIn();

        System.out.println("Type in your password");
        String password = readIn();

        System.out.println("Type in your email");
        String email = readIn();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
            System.out.println("You are not registered. You must type something for your email, password, or username.");
        } else {
            RegisterResult result = SERVER_FACADE.register(username, password, email);

            if (result != null) {
                authToken = result.getAuthToken();
                System.out.println("Registration successful!");
                loggedIn = true;
            } else {
                System.out.println("Registration unsuccessful.");
            }
        }
    }

    private void logoutCommand(){
        LogoutResult result = SERVER_FACADE.logout(authToken);

        if(result == null){
            System.out.println("Unable to logout. If unauthorized error above, you may already be logged out.");
        } else {
            System.out.println("You are logged out.");
            loggedIn = false;
            authToken = null;
        }
    }

    private void createGameCommand(){
        System.out.println("Enter the name of chess game");
        String gameName = readIn();

        for(int game : gameMap.keySet()){
            if(gameMap.get(game).gameName().equals(gameName)){
                System.out.println("Sorry, that name is already taken.");
            }
        }

        System.out.println("Creating game...");
        CreateGameResult result = SERVER_FACADE.createGame(gameName,authToken);

        if(result == null){
            System.out.println("Error creating game. See error messages above.");
        } else {
            System.out.println("Game created!");
        }
    }

    private void listGamesCommand(){
        ListGamesResult result = SERVER_FACADE.listGames(authToken);

        if(result == null){
            System.out.println("Could not access games. See errors above.");
        } else {
            System.out.println("List of games:");
            ArrayList<Game> listGames = (ArrayList<Game>) (SERVER_FACADE.listGames(authToken)).getGames();

            Game currentGame;
            for (var i = 0; i < listGames.size(); i++) {
                currentGame = listGames.get(i);
                gameMap.put((i+1),currentGame);
                System.out.println((i+1)+".  Name: "+currentGame.gameName()+"\n White Player: "+currentGame.whiteUsername()+
                        "\n Black Player: "+currentGame.blackUsername());
            }
        }
    }

    private void playGamesCommand(){
        System.out.println("Type in the number of the desired game.");
        int gameNum = Integer.parseInt(readIn());

        System.out.println("Type in team color you wish to play.");
        String color = readIn();

        ChessGame.TeamColor teamColor = ChessGame.TeamColor.BLACK;
        if(color.equals("white")){
            teamColor = ChessGame.TeamColor.WHITE;
        } else if (color.equals("black")){
            teamColor = ChessGame.TeamColor.BLACK;
        }

        JoinGameResult result = SERVER_FACADE.joinGame(teamColor,gameMap.get(gameNum).gameID(),authToken);

        if(result == null){
            System.out.println("Unable to join game "+gameNum+" as "+color+".");
        } else {
            System.out.println("Successfully joined game "+gameNum+" as "+color+".");
        }
    }

    private void observeGameCommand(){
        System.out.println("Type in the number of the desired game.");
        int gameNum = Integer.parseInt(readIn());

        SERVER_FACADE.observeGame(gameNum);
    }
}
