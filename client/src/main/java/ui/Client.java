package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import model.Game;
import results.*;
import serverfacade.ServerFacadeHttp;
import serverfacade.ServerFacadeWS;

import java.util.*;

public class Client {
    private static boolean loggedIn = false;
    private static final ServerFacadeHttp SERVER_FACADE_HTTP = new ServerFacadeHttp(8080);
    private static final ServerFacadeWS SERVER_FACADE_WS;
    private static String authToken = null;
    public static Map<Integer, Game> gameMap = new HashMap<Integer, Game>();
    private static boolean isInGame = false;
    //private static ChessGame currentChessGame;

    static {
        try {
            SERVER_FACADE_WS = new ServerFacadeWS(8080);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void run(){
        System.out.println("Welcome to Kendall's CS240 chess project! To get started type 'help'.");

        while(true){
            String line = readIn(true);
            
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
                playGameCommand();

            } else if ((line.equals("observegame")) && (loggedIn)) {
                observeGameCommand();
            } else {
                System.out.println("Error: Didn't recognize command. Type in 'help' for a list of commands.");
            }
            //var words = line.split(" ");
        }
    }

    private String readIn(Boolean modify){
        //System.out.printf("%n>>> ");
        System.out.print(">>> ");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();

        if(modify) {
            line = line.toLowerCase();
            line = line.replaceAll("\\s", "");
        }

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
        //Use this to clear the server when you want to.
        /*SERVER_FACADE.clear();
        System.out.println("All users and games deleted.");
        System.out.println("Shutting down.");*/
    }

    private void loginCommand(){
        System.out.println("Type in your username");
        String username = readIn(false);

        System.out.println("Type in your password");
        String password = readIn(false);

        LoginResult result = SERVER_FACADE_HTTP.login(username, password);

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
        String username = readIn(false);

        System.out.println("Type in your password");
        String password = readIn(false);

        System.out.println("Type in your email");
        String email = readIn(false);

        if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
            System.out.println("You are not registered. You must type something for your email, password, or username.");
        } else {
            RegisterResult result = SERVER_FACADE_HTTP.register(username, password, email);

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
        LogoutResult result = SERVER_FACADE_HTTP.logout(authToken);

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
        String gameName = readIn(false);

        for(int game : gameMap.keySet()){
            if(gameMap.get(game).gameName().equals(gameName)){
                System.out.println("Sorry, that name is already taken.");
            }
        }

        System.out.println("Creating game...");
        CreateGameResult result = SERVER_FACADE_HTTP.createGame(gameName,authToken);

        if(result == null){
            System.out.println("Error creating game. See error messages above.");
        } else {
            System.out.println("Game created!");
        }
    }

    private void listGamesCommand(){
        ListGamesResult result = SERVER_FACADE_HTTP.listGames(authToken);

        if(result == null){
            System.out.println("Could not access games. See errors above.");
        } else {
            System.out.println("List of games:");
            ArrayList<Game> listGames = (ArrayList<Game>) (SERVER_FACADE_HTTP.listGames(authToken)).getGames();

            Game currentGame;
            for (var i = 0; i < listGames.size(); i++) {
                currentGame = listGames.get(i);
                gameMap.put((i+1),currentGame);
                System.out.println((i+1)+".  Name: "+currentGame.gameName()+"\n White Player: "+currentGame.whiteUsername()+
                        "\n Black Player: "+currentGame.blackUsername());
            }
        }
    }

    private void playGameCommand(){
        System.out.println("Type in the number of the desired game.");
        int gameNum = Integer.parseInt(readIn(true));

        System.out.println("Type in team color you wish to play.");
        String color = readIn(true);

        ChessGame.TeamColor teamColor = null;
        if(color.equals("white")){
            teamColor = ChessGame.TeamColor.WHITE;
        } else if (color.equals("black")){
            teamColor = ChessGame.TeamColor.BLACK;
        } else {
            System.out.println("Team color invalid. Unable to join game " + gameNum + " as " + color + ".");
            return;
        }

        Game gameOfInterest = gameMap.get(gameNum);
        if(gameOfInterest == null){
            System.out.println("This game doesn't exist.");
        } else {
            JoinGameResult result = SERVER_FACADE_HTTP.joinGame(teamColor, gameMap.get(gameNum).gameID(), authToken);

            if (result == null) {
                System.out.println("Unable to join game " + gameNum + " as " + color + ".");
            } else {
                System.out.println("Successfully joined game " + gameNum + " as " + color + ".");
                gamePlay(teamColor, gameOfInterest);
            }
        }
    }

    private void gamePlay(ChessGame.TeamColor teamColor, Game gameOfInterest){
        String line;

        isInGame = true;
        while(isInGame){
            line = readIn(true);

            if(line.equals("help")){
                System.out.println("Redraw Chess Board->Redraws the chess board");
                System.out.println("Leave->Removes user from the game, but doesn't forfeit the game.");
                System.out.println("Make Move->Makes a move.");
                System.out.println("Resign->User forfeits the game.");
                System.out.println("Highlight Legal Moves->Shows the possible moves of the selected piece.");
            } else if (line.equals("redrawchessboard")) {
                //ChessGame newChessGame = SERVER_FACADE_WS.redrawChessBoard();

                DisplayBoard.main(ChessGame.TeamColor.WHITE, gameOfInterest.game());
                DisplayBoard.main(ChessGame.TeamColor.BLACK, gameOfInterest.game());

            } else if (line.equals("leave")) {
                isInGame = false;
                SERVER_FACADE_WS.leave(authToken,gameOfInterest.gameID());

            } else if (line.equals("makemove")){
                System.out.println("Please enter the location of the piece you want to move (e.g. b4).");
                line = readIn(true);

                int col = getColumn(line);
                int row = Integer.valueOf(line.charAt(1));
                ChessPosition startPos = new ChessPosition(row,col);


                System.out.println("Please enter the location of the piece you want to move (e.g. b4).");
                line = readIn(true);

                col = getColumn(line);
                row = Integer.valueOf(line.charAt(1));
                ChessPosition secondMove = new ChessPosition(row,col);



                //SERVER_FACADE_WS.makeMove(new ChessMove());
                
            } else if (line.equals("resign")){
                isInGame = false;
                SERVER_FACADE_WS.resign(authToken, gameOfInterest.gameID());
                
            } else if (line.equals("highlightlegalmoves")) {
                
            } else {
                System.out.println("Not a valid command.");
            }
        }
    }

    private Integer getColumn(String input){
        String firstChar = String.valueOf(input.charAt(0));
        return switch (firstChar) {
            case "a" -> 1;
            case "b" -> 2;
            case "c" -> 3;
            case "d" -> 4;
            case "e" -> 5;
            case "f" -> 6;
            case "g" -> 7;
            case "h" -> 8;
            default -> null;
        };
    }

    private ChessMove validMoves(ChessMove chessMove, ChessGame chessGame) {
        ChessPiece pieceOfInterest = chessGame.getBoard().getPiece(chessMove.getStartPosition());

        ArrayList<ChessMove> validMoves = (ArrayList<ChessMove>) chessGame.validMoves(chessMove.getStartPosition());

        /*for(validMoves : ChessMove move = null){
            if(move.getEndPosition() == chessMove.getEndPosition()){
                return move;
            }
        }*/

        return chessMove;
    }

    private void observeGameCommand(){
        System.out.println("Type in the number of the desired game.");
        int gameNum = Integer.parseInt(readIn(true));

        SERVER_FACADE_HTTP.observeGame(gameNum);
    }
}
