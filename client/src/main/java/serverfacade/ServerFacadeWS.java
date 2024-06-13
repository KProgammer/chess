package serverfacade;

//import org.eclipse.jetty.websocket.api.Session;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import websocket.commands.ResignCommand;
import websocket.messages.LoadGameMessage;

import javax.websocket.*;
import java.net.URI;

public class ServerFacadeWS extends Endpoint {

    public javax.websocket.Session session;

    public ServerFacadeWS(Integer port) throws Exception {
        URI uri = new URI("ws://localhost:"+port+"/ws");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                System.out.println(message);
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public ChessGame redrawChessBoard(){
        try {
            send("redraw");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ChessGame();
    }

    public void leave(String authToken, Integer gameID) {
        try {
            send(new Gson().toJson(new ResignCommand(authToken,gameID,false)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void makeMove(ChessMove chessMove) {
        try {
            send(new Gson().toJson(chessMove));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void resign(){
        try {
            send("resign");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
