import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Client {

    public void run(){
        System.out.println("Welcome to Kendall's CS240 chess project! To get started type 'help'.");

        Boolean loggedIn = false;
        ServerFacade serverFacade;

        try {
            serverFacade = new ServerFacade(new URI("http://localhost:8080"));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        while(true){
            System.out.printf("Type here%n>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            line = line.toLowerCase();
            line = line.replaceAll("\\s","");
            
            if(line.equals("help")){

                if (loggedIn == true){
                    //Wahoo!
                } else {
                    System.out.println("quit-> exits the program");
                    System.out.println("login-> prompts user for login information and logs user in.");
                    System.out.println("register-> prompts user for registration information and registers the user.");
                }
                
            } else if (line.equals("quit")) {
                serverFacade.stop();
                
            } else if (line.equals("login")) {
                serverFacade.login(URI.create("http://localhost:8080/session"));
                
            } else if (line.equals("register")) {
                
            } else if (line.equals("logout")) {
                serverFacade.logout(URI.create("http://localhost:8080/session"));

            } else if (line.equals("creategame")) {
                serverFacade.createGame(URI.create("http://localhost:8080/game"));
            } else if (line.equals("listgames")) {
                serverFacade.listGames(URI.create("http://localhost:8080/game"));
                
            } else if (line.equals("playgame")) {
                serverFacade.joinGame(URI.create("http://localhost:8080/game"));

            } else if (line.equals("observegame")) {

            }

            var words = line.split(" ");


        }
    }
}
