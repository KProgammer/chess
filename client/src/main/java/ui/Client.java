package ui;

import chess.*;
import model.Authorization;
import model.Game;
import results.*;
import serverfacade.ServerFacadeHttp;
import serverfacade.ServerFacadeWS;

import javax.print.DocFlavor;
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
        /*SERVER_FACADE_HTTP.clear();
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
        int gameNum = getGameNumberFromUser(readIn(true));

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
            SERVER_FACADE_WS.connect(authToken, gameMap.get(gameNum).gameID());
            if (result == null) {
                System.out.println("Unable to join game " + gameNum + " as " + color + ".");
            } else {
                System.out.println("Successfully joined game " + gameNum + " as " + color + ".");
                gamePlay(teamColor, gameOfInterest.gameID(), gameNum);
            }
        }
    }

    private void gamePlay(ChessGame.TeamColor teamColor, Integer gameID, Integer gameListNum){
        String line;
        ChessPiece promotionPiece = null;

        isInGame = true;
        while(isInGame){
            line = readIn(true);
            Game gameOfInterest = gameMap.get(gameListNum);

            if(line.equals("help")){
                System.out.println("Redraw Chess Board->Redraws the chess board");
                System.out.println("Leave->Removes user from the game, but doesn't forfeit the game.");
                System.out.println("Make Move->Makes a move.");
                System.out.println("Resign->User forfeits the game.");
                System.out.println("Highlight Legal Moves->Shows the possible moves of the selected piece.");
            } else if (line.equals("redrawchessboard")) {
                DisplayBoard.main(teamColor, gameOfInterest.game(), null);

            } else if (line.equals("leave")) {
                leaveGameCommand(gameOfInterest.gameID());
                isInGame = false;

            } else if (line.equals("makemove")){
                System.out.println("Please enter the location of the piece you want to move (e.g. b4).");
                line = readIn(true);

                ChessPosition startPos = getChessPositionFromUser(line);

                System.out.println("Please enter the location of the piece you want to move (e.g. b4).");
                line = readIn(true);

                ChessPosition secondMove = getChessPositionFromUser(line);

                ArrayList<ChessMove> movesThatMatch = new ArrayList<>();
                ArrayList<ChessMove> validMoves = (ArrayList<ChessMove>) gameOfInterest.game().validMoves(startPos);

                if(validMoves == null){
                    System.out.println("Invalid move.");
                    continue;
                }

                for(ChessMove move : validMoves){
                    if(Objects.equals(move.getStartPosition(), startPos) &&
                    Objects.equals(move.getEndPosition(), secondMove)) {
                        movesThatMatch.add(move);
                    }
                }

                if (movesThatMatch.size() > 1){
                    System.out.println("Please enter the piece you wish to promote your pawn to move (e.g. queen)." +
                            "If you do not enter a piece, it will be automatically promoted to a queen.");
                    line = readIn(true);
                    switch (line){
                        case "rook" -> promotionPiece = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
                        case "knight" -> promotionPiece = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
                        case "bishop" -> promotionPiece = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
                        default ->  promotionPiece = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);

                    }
                } else if(movesThatMatch.isEmpty()){
                    System.out.println("Invalid move. Use the 'highlight legal moves' command to see possible moves");
                    continue;
                }

                if(promotionPiece != null) {
                    SERVER_FACADE_WS.makeMove(authToken, gameID, new ChessMove(startPos, secondMove, promotionPiece.getPieceType()));
                } else {
                    SERVER_FACADE_WS.makeMove(authToken, gameID, new ChessMove(startPos, secondMove, null));
                }
                
            } else if (line.equals("resign")){
                System.out.println("Are you sure you want to forfeit? Y/N");
                line = readIn(true);

                if(line.equals("y")) {
                    SERVER_FACADE_WS.resign(authToken, gameOfInterest.gameID());
                } else {
                    System.out.println("Okay! Resume playing. You got this!");
                }
                
            } else if (line.equals("highlightlegalmoves")) {
                highlightLegalMoves(gameOfInterest);
                
            } else {
                System.out.println("Not a valid command.");
            }

            promotionPiece = null;
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
            default -> 0;
        };
    }

    private void observeGameCommand(){
        System.out.println("Type in the number of the desired game.");

        int gameNum;
        try {
            gameNum = Integer.parseInt(readIn(true));
        } catch (NumberFormatException numberFormatException){
            System.out.println("Invalid input.");
            return;
        }

        if (gameMap.get(gameNum) != null) {
            SERVER_FACADE_WS.connect(authToken, gameMap.get(gameNum).gameID());
        } else {
            System.out.println("Not a valid game. Use the list games command to either find the correct number or " +
                    "refresh the list of commands.");
            return;
        }


        String line;
        boolean isInGame = true;
        while(isInGame){
            line = readIn(true);
            Game gameOfInterest = gameMap.get(gameNum);

            if(line.equals("help")){
                System.out.println("Redraw Chess Board->Redraws the chess board");
                System.out.println("Leave->Removes user from the game, but doesn't forfeit the game.");
                System.out.println("Highlight Legal Moves->Shows the possible moves of the selected piece.");
            } else if (line.equals("redrawchessboard")) {
                DisplayBoard.main(gameOfInterest.game().getTeamTurn(), gameOfInterest.game(), null);
            } else if (line.equals("leave")) {
                leaveGameCommand(gameOfInterest.gameID());
                isInGame = false;

            } else if (line.equals("highlightlegalmoves")) {
                highlightLegalMoves(gameOfInterest);
            } else {
                System.out.println("Not a valid command.");
            }
        }
    }

    private void leaveGameCommand(int gameID){
        SERVER_FACADE_WS.leave(authToken, gameID);
        System.out.println("You have left the game.");
    }

    private void highlightLegalMoves(Game gameOfInterest){
        String line;
        System.out.println("Please enter the location of the piece you wish to see possible moves for (e.g. b4).");
        line = readIn(true);

        ChessPosition posOfInterest = getChessPositionFromUser(line);

        DisplayBoard.main(gameOfInterest.game().getTeamTurn(),gameOfInterest.game(),posOfInterest);
    }

    private ChessPosition getChessPositionFromUser(String input){
        while ((getColumn(input) == 0) || (!Character.isDigit(input.charAt(1)))) {
            System.out.println("Invalid input, the format is column and then row. (e.g. b4)");
            input = readIn(true);
        }

        int col = getColumn(input);
        int row = Integer.parseInt(String.valueOf(input.charAt(1)));

        return new ChessPosition(row,col);
    }

    private int getGameNumberFromUser(String input){
        boolean isInt = false;
        while (!isInt) {
            try {
                isInt = true;
                Integer.parseInt(input);
            } catch (NumberFormatException e){
                System.out.println("Invalid input, try again.");
                input = readIn(true);
                isInt = false;
            }
        }
        return Integer.parseInt(input);
    }
}
