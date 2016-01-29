import java.util.Scanner;
import java.util.ArrayList;
import java.net.URL;
 
public class Wiki {

    public static void main(String[] args)
    {
        try
        {
            Scanner scan =  new Scanner(System.in);
            System.out.println("Please enter a search term(eg. java_(programming language))");
            String input = scan.nextLine();
            input = input.replaceAll(" ", "_").toLowerCase();
            System.out.println("https://en.wikipedia.org/wiki/" + input);
            URL my_url = new URL("https://en.wikipedia.org/wiki/" + input);
            Parser parse = new Parser(my_url);
            
            ArrayList<String> webPage = parse.get(my_url);
            parse.display(webPage);
        }

        catch (Exception ex) {
          System.out.println("Uh Oh! AN Error Occured!");
      }
    }
}