package serverfacade;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import model.Game;
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

import static ui.Client.gameMap;
import static websocket.messages.ServerMessage.ServerMessageType.*;

public class ServerFacadeWS extends Endpoint {

    public javax.websocket.Session session;

    public ServerFacadeWS(Integer port) throws Exception {
        URI uri = new URI("ws://localhost:"+port+"/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                //ServerMessage.ServerMessageType type = serverMessage.getServerMessageType();
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

    public void onOpen(Session session, EndpointConfig endpointConfig) {
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
        Game gameOfInterest = gameMap.get(loadGameMessage.getGameID());

        gameOfInterest = new Game(gameOfInterest.gameID(),gameOfInterest.whiteUsername(),gameOfInterest.blackUsername(),
                gameOfInterest.gameName(),loadGameMessage.getGame());

        gameMap.put(loadGameMessage.getGameID(),gameOfInterest);

        if(gameOfInterest.game().isInCheckmate(ChessGame.TeamColor.BLACK)){
            try {
                send(new Gson().toJson(new NotificationMessage("has won")));
                send(new Gson().toJson(new NotificationMessage("has won")));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (gameOfInterest.game().isInCheckmate(ChessGame.TeamColor.WHITE)){

        } else if (gameOfInterest.game().isInCheck(ChessGame.TeamColor.BLACK)) {

        } else if (gameOfInterest.game().isInCheck(ChessGame.TeamColor.WHITE)) {

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
