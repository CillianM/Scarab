package ieminisham.dcu.redbrick.httpwww.scarab;

import android.widget.TextView;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;



public class Parser
{
    URL url = null;
    TextView textView = null;
    ArrayList<String> links = new ArrayList<>();
    ArrayList<String> webPage = new ArrayList<>();

    //basic constructors
    public Parser(String input,TextView instantiation)
    {
        try
        {
            //make input more "wiki friendly"
            input = input.replaceAll(" ", "_").toLowerCase();
            url = new URL("https://en.wikipedia.org/wiki/" + input);
            textView = instantiation;
        }

        //catch any exceptions thrown
        catch (Exception ex) {
            textView.append("The following error occured: \n" + ex);
        }
    }

    public ArrayList<String> search()
    {
        try
        {
            get(url); //get page from constructed url

            //print off actual page to textField
            for(int i = 0; i < webPage.size(); i++)
            {
                textView.append(webPage.get(i));
            }

            return links;
        }


        //catch any exceptions thrown
        catch (Exception ex) {
            System.out.println("The following error occured: \n" + ex);
        }

        return null;
    }

    //get the url's data and put it in an arraylist to be returned
    public void get(URL inputUrl)
    {
        try
        {
            url = inputUrl;
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp;
            int count = 0;
            while(null != (strTemp = br.readLine()))
            {
                strTemp = extract(strTemp);
                if(strTemp.equals("ReferencesEdit"))
                    break;
                if(strTemp.length() < 2 && count == 0)
                {
                    count = 1;
                    webPage.add(strTemp + "\n");
                }
                else if(strTemp.length() > 20)
                {
                    webPage.add(strTemp + "\n");
                    count = 0;
                }
            }
        }

        catch (Exception ex)
        {
            textView.append("The following error occured: \n" + ex);
        }
    }

    //removes braces, basic cleanup of data
    public String extract(String line)
    {

        if(line.contains("href"))
            getLink(line);

        line = line.replaceAll("<.*?>","");
        line = line.trim();

        if(line.contains("{")|line.contains("}")|line.contains("$"))
            return "";

        return line;
    }

    public void getLink(String potLink)
    {
        int start;
        int end;
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
                                //add it to our arraylist of links if it looks like an address of somekind
                                if(potLink.contains("/wiki"))
                                    links.add(potLink);
                                break;
                            }
                        }
                    }
                    //try and get the point where the quotes end in the link
                }
            }
        }

    }
}