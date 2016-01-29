import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.net.URL;
import java.io.Serializable;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

class Scarab extends JFrame implements ActionListener
{
	JPanel panel;
	JToolBar toolBar;
	JTextArea textArea;
	JTextField textField;
	JButton search;
	JButton save;
	JButton open;
	JButton clear;
	JLabel summary;
	int startList;
	int endList;
	
	public Scarab()
	{
		//constructors to set actual window
		super("Scarab");
		setSize(900,600);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//instantiate variables
		JPanel panel = new JPanel();
		textArea = new JTextArea();
		toolBar = new JToolBar();
		save = new JButton("Save");
		open = new JButton("Open");
		clear = new JButton("Clear");
		search = new JButton("Search");	
		textField = new JTextField("",20);
		summary = new JLabel("Enter term to search/save/open");
		
		//scrollbar for when the window is too small
		JScrollPane scroller = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//add action listeners to be used when button is clicked
		open.addActionListener(this);
		search.addActionListener(this);
		save.addActionListener(this);
		clear.addActionListener(this);
		
		//add items in desired order
		panel.setLayout(new BorderLayout());
		toolBar.add(summary);
		toolBar.add(textField);
		toolBar.add(search);
		toolBar.add(clear);
		toolBar.add(save);
		toolBar.add(open);
		panel.add(toolBar,BorderLayout.NORTH);
		panel.add(scroller,BorderLayout.CENTER);
		add(panel);
		setVisible(true);
		
	}
	
	//prints desired text to field
	public void printToField(String text)
	{
		textArea.append(text + "\n");
	}
	
	//SUPPOSED TO clear text from field
	public void clearField()
	{
		textArea.setText(null);
	}
	
	//checks if any action is performed(is a button pressed?)
	public void actionPerformed(ActionEvent e)
	{
		//if it's open get the filename from the input and pull text from it 
		if(e.getSource() == open)
		{
			try
			{
				File file = new File(textField.getText() + ".html");
				Scanner scan = new Scanner(file);
				System.out.println("reading file " + textField.getText() + ".html");
				while(scan.hasNextLine())
				{
					printToField(scan.nextLine());
				}
			}
			catch(FileNotFoundException f)
			{
				System.out.println("Error reading file " + textField.getText() + ".html");
			}
		}
		
		//SUPPOSED TO clear the text field
		if(e.getSource() == clear)
		{
			textArea.setText(null);
			textField.setText(null);
		}
		
		//Calls to save class to save file
		else if(e.getSource() == save)
		{
			if(textField.getText().length() > 0)
			{
				fileManager fm = new fileManager(textField.getText() + ".html");
				fm.save(textArea.getText());
			}
		}
		
		//creates appropriate url and sends it away to be parsed
		//Worth looking over and cleaning up
		else if(e.getSource() == search)
		{
			 try
			{
				clearField();
				textArea.setText("");
				String input = textField.getText();
				if(input.length() >= 1)
				{
					//make input more "wiki friendly" 
					input = input.replaceAll(" ", "_").toLowerCase();
					
					//allow user to see link constructed and make it clickable for html page
					printToField("<a href =\"https://en.wikipedia.org/wiki/" + input +"\">" + input + "</a><br>");
					URL my_url = new URL("https://en.wikipedia.org/wiki/" + input);
					
					//Parser object for getting page and extraction
					Parser parse = new Parser(my_url);
					ArrayList<String> webPage = parse.get(my_url); //get page from constructed url
					
					//get range that the desired text lies in
					int [] range = parse.getArray(webPage);
					startList = range[0];
					endList = range[1];
					
					//tag for html compatibility when saved
					printToField("<p>");
					
					//print off actual page to textField
					for(int i = startList; i < endList; i++)
					{
						//extraction process, remove tags, trim, get links 
						String tmp = parse.extract(webPage.get(i));
						if(tmp.length() > 4)
							printToField(tmp + "<br>"); // br tag for html newline
					}
					
					//Print off any links saved from extraction
					printToField("<h1>\n" + "Here are some links collected from the page:</h1>");
					ArrayList <String> links = parse.returnLinks();
					for(int j = 0; j < links.size(); j++)
					{
						//contruct link to be clickable in html page
						String tmp = "<a href =\"https://en.wikipedia.org" + links.get(j) +"\">" + links.get(j) + "</a><br>";
						printToField(tmp);
					}
					//end html text
					printToField("</p>");
				}
			}
			
			//catch any exceptions thrown
			catch (Exception ex) 
			{
			  System.out.println(ex);
			}
		}
	}
	
	//return current version (i have this because i like to feel official alright? get off my back :P) 
	public String version()
	{
		return "Scarab version 3.3";
	}
	
}