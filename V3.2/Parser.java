import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class Parser
{
  URL url = null;
  ArrayList<String> parsedList;
  int startList = 0;
  int endList = 0;
  
  //basic constructors
  public Parser(URL url)
  {
    this.url = url;
  }

  public Parser()
  {
    this.url = null;
  }
  
  //get the url's data and put it in an arraylist to be returned
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
  
  //set the url
  public void set(URL inputUrl)
  {
    url = inputUrl;
  }
  
//gets the area that the data we want lies in
public int [] getArray(ArrayList<String> list)
{
  int start = 0;
  int end = 0;
  String openBrace = "<div id= \"bodyContent\" class=\"mw-body-content\">";
  String closeBrace = "NewPP limit report";

  for(int i = 0; i < list.size(); i++)
  {
    String temp = list.get(i);
    if(temp.equals(openBrace))
    {
      start = i + 1;
      startList = start;
    }


  }

  for(int j = start; j < list.size(); j++)
  {
    String temp = list.get(j);
    if(temp.equals(closeBrace))
    {
      end = j;
      endList = end;
      break;
    }
    extract(temp);
  }

  int [] array = {start,end};
  parsedList = list;
  return array;
}

//removes braces, basic cleanup of data
public String extract(String line)
{
	line = line.replaceAll("\\<.*?>","");
  line = line.trim();
  if(line.contains("{")|line.contains("}")|line.contains("$"))
    line = "";
	return line;
}
}