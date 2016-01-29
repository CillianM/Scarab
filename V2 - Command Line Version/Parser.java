import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Parser
{
  URL url = null;
  
  public Parser(URL url)
  {
    this.url = url;
  }

  public Parser()
  {
    this.url = null;
  }
  
  public ArrayList<String> get(URL inputUrl)
  {
      ArrayList<String> page = new ArrayList<>();
      try
      {
          url = inputUrl;
          BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
          String strTemp = "";
          while(null != (strTemp = br.readLine()))
          {
              page.add(strTemp);
              
          }

          return page;

      } catch (Exception ex) {
          System.out.println("Uh Oh! AN Error Occured!");
      }

      return null;
  }
  
  public void set(URL inputUrl)
  {
    url = inputUrl;
  }
  
  public void display(ArrayList<String> list)
  {
      int index = 0;
      String openBrace = "<div id= \"bodyContent\" class=\"mw-body-content\">";
      String closeBrace = "NewPP limit report";

      for(int i = 0; i < list.size(); i++)
      {
          String temp = list.get(i);
          if(temp.equals(openBrace))
              index = i + 1;
      }

      for(int j = index; j < list.size(); j++)
      {
          String temp = list.get(j);
          if(temp.equals(closeBrace))
              break;
          extract(temp);
      }
  }
  
  public void extract(String line)
  {
    line = line.replaceAll("\\<.*?>","");
    System.out.println(line);
  }
}