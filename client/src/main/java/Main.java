import chess.*;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.LowerCase;

import java.io.IOException;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        //var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        //System.out.println("♕ 240 Chess Client: " + piece);

        //DisplayBoard.main(args, ChessGame.TeamColor.WHITE, new ChessGame());
        //DisplayBoard.main(args, ChessGame.TeamColor.BLACK, new ChessGame());

        //Supposedly this code starts up a server, however Repl doesn't work.
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        Client client = new Client();

        client.run();

    }
}