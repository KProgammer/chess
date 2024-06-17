package serverfacade;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import model.Game;
import ui.DisplayBoard;
import websocket.commands.ConnectCommand;
import websocket.commands.LeaveCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.ResignCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.net.URI;
import java.util.ArrayList;

import static ui.Client.gameMap;
import static websocket.messages.ServerMessage.ServerMessageType.*;

public class ServerFacadeWS extends Endpoint {

    public javax.websocket.Session session;

    public ServerFacadeWS(Integer port) throws Exception {
        URI uri = new URI("ws://localhost:"+port+"/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);

                switch (serverMessage.getServerMessageType()){
                    case LOAD_GAME -> loadGame(message);
                    case ERROR -> error(message);
                    case NOTIFICATION -> notification(message);
                }
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }

    public void connect(String authToken, Integer gameID){
        try {
            send(new Gson().toJson(new ConnectCommand(authToken, gameID)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void leave(String authToken, Integer gameID) {
        try {
            send(new Gson().toJson(new LeaveCommand(authToken,gameID)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove(String authToken, Integer gameID, ChessMove chessMove) {
        try {
            send(new Gson().toJson(new MakeMoveCommand(authToken, gameID, chessMove)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void resign(String authToken, Integer gameID){
        try {
            send(new Gson().toJson(new ResignCommand(authToken, gameID)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void loadGame(String message){
        LoadGameMessage loadGameMessage = new Gson().fromJson(message, LoadGameMessage.class);

        Game gameOfInterest = null;

        int refNum = 0;

        for(int gameNum : gameMap.keySet()){
            if(gameMap.get(gameNum).gameID() != null &&
                    gameMap.get(gameNum).gameID().equals(loadGameMessage.getGameID())){

                gameOfInterest = gameMap.get(gameNum);
                refNum = gameNum;
            }
        }

        if(gameOfInterest == null &&
                refNum == 0){
            System.out.println("LoadGameMessage gave us a game that doesn't exist");
            return;
        }

        try {
            gameMap.put(refNum, new Game(gameOfInterest.gameID(),gameOfInterest.whiteUsername(),gameOfInterest.blackUsername(),
                    gameOfInterest.gameName(),loadGameMessage.getGame()));

            if(loadGameMessage.getTeamColor() == null) {
                DisplayBoard.main(loadGameMessage.getGame().getTeamTurn(), gameMap.get(refNum).game(), null);
            } else {
                DisplayBoard.main(loadGameMessage.getTeamColor(), gameMap.get(refNum).game(), null);
            }

            /*if(loadGameMessage.getBlackHasWon()){
                System.out.println("Black has won!");
            } else if(loadGameMessage.getWhiteHasWon()){
                System.out.println("White has won!");
            } else if(loadGameMessage.getStalemate()){
                System.out.println("Stalemate");
            }*/

        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public void error(String message){
        ErrorMessage errorMessage = new Gson().fromJson(message, ErrorMessage.class);
        System.out.println(errorMessage.getErrorMessage());

    }

    public void notification(String message){
        NotificationMessage notificationMessage = new Gson().fromJson(message, NotificationMessage.class);

        System.out.println(notificationMessage.getMessage());
    }

}
