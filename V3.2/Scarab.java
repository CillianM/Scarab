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
	ArrayList<String> page;
	int startList;
	int endList;
	
	public Scarab()
	{
		//constructors to set actual window
		super("Scarab");
		setSize(400,300);
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
		
		//scrollbar for when the window is too small
		JScrollPane scroller = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//add action listeners to be used when button is clicked
		open.addActionListener(this);
		search.addActionListener(this);
		save.addActionListener(this);
		
		//add items in desired order
		panel.setLayout(new BorderLayout());
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
				File file = new File(textField.getText() + ".txt");
				Scanner scan = new Scanner(file);
				System.out.println("reading file " + textField.getText() + ".txt");
				while(scan.hasNextLine())
				{
					printToField(scan.nextLine());
				}
			}
			catch(FileNotFoundException f)
			{
				System.out.println("Error reading file " + textField.getText() + ".txt");
			}
		}
		
		//SUPPOSED TO clear the text field
		if(e.getSource() == clear)
		{
			textArea.setText(null);
		}
		
		//Calls to save class to save file
		else if(e.getSource() == save)
		{
			
			fileManager fm = new fileManager(textField.getText() + ".txt");
			fm.save(textArea.getText());
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
					input = input.replaceAll(" ", "_").toLowerCase();
					printToField("https://en.wikipedia.org/wiki/" + input);
					URL my_url = new URL("https://en.wikipedia.org/wiki/" + input);
					
					Parser parse = new Parser(my_url);
	            
					ArrayList<String> webPage = parse.get(my_url);
					page = webPage;
					int [] range = parse.getArray(webPage);
					startList = range[0];
					endList = range[1];
					for(int i = startList; i < endList; i++)
					{
						String tmp = parse.extract(webPage.get(i));
						if(tmp.length() > 1)
							printToField(tmp);
					}
				}
			}

			catch (Exception ex) 
			{
			  System.out.println("Uh Oh! AN Error Occured!");
			}
		}
	}
}