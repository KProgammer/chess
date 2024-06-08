import chess.ChessGame;
import model.Game;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public void run(){
        System.out.println("Welcome to Kendall's CS240 chess project! To get started type 'help'.");

        boolean loggedIn = false;
        ServerFacade serverFacade;
        String authToken = null;
        serverFacade = new ServerFacade();

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
                System.out.println("Logged out and shutting down.");

                loggedIn = false;
                serverFacade.logout(URI.create("http://localhost:8080/session"),authToken);
                serverFacade.clear(URI.create("http://localhost:8080/db"));
                break;
                
            } else if (line.equals("login")) {
                System.out.println("Type in your username");
                String username = readIn();

                System.out.println("Type in your password");
                String password = readIn();

                authToken = (serverFacade.login(URI.create("http://localhost:8080/session"),username, password)).getAuthToken();
                loggedIn = true;
                
            } else if (line.equals("register")) {
                System.out.println("Type in your username");
                String username = readIn();

                System.out.println("Type in your password");
                String password = readIn();

                System.out.println("Type in your email");
                String email = readIn();

                serverFacade.register(URI.create("http://localhost:8080/user"), username, password, email);
                
            } else if (line.equals("logout")) {
                System.out.println("Logged out.");
                serverFacade.logout(URI.create("http://localhost:8080/session"),authToken);

                loggedIn = false;
                authToken = null;

            } else if ((line.equals("creategame")) && (loggedIn)) {
                System.out.println("Enter the name of chess game");
                String gameName = readIn();

                System.out.println("Creating game...");
                int gameID = serverFacade.createGame(URI.create("http://localhost:8080/game"),gameName,authToken);
                System.out.println("Game created! gameID is: "+gameID);

            } else if ((line.equals("listgames")) && (loggedIn)) {
                System.out.println("List of games:");
                ArrayList<Game> listGames = (ArrayList<Game>) serverFacade.listGames(URI.create("http://localhost:8080/game"),authToken);
                for (var i = 0; i < listGames.size(); i++) {
                    //System.out.printf("%d. %s%n", i+1, listGames.get(i));
                    System.out.println(listGames.get(i));
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

                serverFacade.joinGame(URI.create("http://localhost:8080/game"),teamColor,gameID,authToken);

            } else if ((line.equals("observegame")) && (loggedIn)) {
                serverFacade.observeGame();
            } else {
                System.out.println("Error: Didn't recognize command. Try again.");
            }
            //var words = line.split(" ");
        }
    }

    private String readIn(){
        System.out.printf("Type here%n>>> ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        line = line.toLowerCase();
        line = line.replaceAll("\\s","");

        return line;
    }
}
