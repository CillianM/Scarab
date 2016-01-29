import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
 
/**
*
* @author vimal
*/
public class Main {
 
    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) 
    {
        ArrayList<String> list = new ArrayList<>();
        Scanner scan =  new Scanner(System.in);
        try
        {
            System.out.println("Please enter url(eg. http://www.redbrick.dcu.ie/~minisham/JS");
            String input = scan.next();
            URL my_url = new URL(input);
            BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
            String strTemp = "";
            while(null != (strTemp = br.readLine()))
            {
                list.add(strTemp);
                
            }

            display(list);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void display(ArrayList<String> list)
    {
        int index = 0;

        for(int i = 0; i < list.size(); i++)
        {
            String temp = list.get(i);
            if(temp.equals("<body>"))
                index = i + 1;
        }

        for(int j = index; j < list.size(); j++)
        {
            String temp = list.get(j);
            if(temp.equals("</body>"))
                break;
            System.out.println(temp);
        }
    }
}