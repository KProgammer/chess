package websocket.messages;

public class NotificationMessage extends ServerMessage {
    public final String message;

    public NotificationMessage(String message){
        super(ServerMessageType.NOTIFICATION);
        this.message = message;
    }

    public final String getMessage() {
        return message;
    }
}
