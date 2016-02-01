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
  ArrayList<String> links = new ArrayList<>();
  
  //basic constructors
  public Parser(URL url)
  {
    this.url = url;
  }

  public Parser()
  {
    this.url = null;
  }

  public void search(Scarab scarab)
  {
	try
	{

		ArrayList<String> webPage = get(url); //get page from constructed url
		
		//get range that the desired text lies in
		int [] range = getArray(webPage);
		startList = range[0];
		endList = range[1];
		
		//tag for html compatibility when saved
		scarab.printToField("<p>");
		
		//print off actual page to textField
		for(int i = startList; i < endList; i++)
		{
			//extraction process, remove tags, trim, get links 
			String tmp = extract(webPage.get(i));
			if(tmp.length() > 4)
				scarab.printToField(tmp + "<br>"); // br tag for html newline
		}
		
		//Print off any links saved from extraction
		scarab.printToField("<h1>\n" + "Here are some links collected from the page:</h1>");
		ArrayList <String> links = returnLinks();
		for(int j = 0; j < links.size(); j++)
		{
			//contruct link to be clickable in html page
			String tmp = "<a href =\"https://en.wikipedia.org" + links.get(j) +"\">" + links.get(j) + "</a><br>";
			scarab.printToField(tmp);
		}
		//end html text
		scarab.printToField("</p>");
	}
   

	//catch any exceptions thrown
	catch (Exception ex) 
	{
	  System.out.println("The following error occured: \n" + ex);
	}
   }
  

  public ArrayList<String> returnLinks()
  {
	  return links;
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
          System.out.println("The following error occured: \n" + ex);
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
		if(line.contains("href"))
			getLink(line);
		line = line.replaceAll("\\<.*?>","");
		line = line.trim();
		if(line.contains("{")|line.contains("}")|line.contains("$"))
			line = "";
		return line;
	}

	public void getLink(String potLink)
	{
		int start = 0;
		int end = 0;
		for(int i = 0; i < potLink.length(); i++)
		{
			//pointless after this point
			if(potLink.length() - i < 10)
				break;
			//check if has similarities to start of <a href="...
			if(potLink.charAt(i) == '<' && potLink.charAt(i+1) == 'a')
			{
				for(int j = i; j < potLink.length(); j++)
				{
					//get the point right after the = in the link
					if(potLink.charAt(j) == '=' && potLink.charAt(j-1) == 'f')
					{
						start = j+2;
						for(int k = start; k < potLink.length(); k++)
						{
							if(potLink.charAt(k)  == '"')
							{
								end = k;
								potLink = potLink.substring(start,end);
								break;
							}
						}
					}
					//try and get the point where the quotes end in the link
				}
			}
		}
		//add it to our arraylist of links if it looks like an address of somekind
		if(potLink.charAt(0) == '/')
			links.add(potLink);
	}
}