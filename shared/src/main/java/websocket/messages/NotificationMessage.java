package websocket.messages;

public class NotificationMessage {
    public static String message;

    public NotificationMessage(String message){
        this.message = message;
    }

    public static String getMessage() {
        return message;
    }
}
