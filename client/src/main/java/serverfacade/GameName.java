package serverfacade;

import model.Game;

public class GameName {
    private String gameName;

    public GameName(String gameName){
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String newGameName){
        gameName = newGameName;
    }
}
