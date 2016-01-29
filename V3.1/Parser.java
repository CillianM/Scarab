import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

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
		start = i + 1;
	}

	for(int j = start; j < list.size(); j++)
	{
		String temp = list.get(j);
		if(temp.equals(closeBrace))
		{
			end = j;
			break;
		}
		extract(temp);
	}
	
	int [] array = {start,end};
	return array;
}

public String extract(String line)
{
	line = line.replaceAll("\\<.*?>","");
	return line;
}
}