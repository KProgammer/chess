import java.io.InputStream;
import java.util.Scanner;

public class Client {

    public void run(){
        System.out.println("Welcome to Kendall's CS240 chess project! To get started type 'help'.");

        while(true){
            System.out.printf("Type here%n>>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            
            if(line.equals("help")){
                
            } else if (line.equals("")) {
                
            }

            var words = line.split(" ");


        }
    }
}
