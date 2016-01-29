import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.net.URL;

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
	
	public Scarab()
	{
		super("Scarab");
		setSize(400,300);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		textArea = new JTextArea();
		toolBar = new JToolBar();
		save = new JButton("Save");
		open = new JButton("Open");
		clear = new JButton("Clear");
		search = new JButton("Search");	
		textField = new JTextField("",20);
		
		JScrollPane scroller = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		open.addActionListener(this);
		search.addActionListener(this);
		save.addActionListener(this);
		
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
	
	public void printToField(String text)
	{
		textArea.append(text + "\n");
	}
	
	public void clearField()
	{
		textArea.setText("");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == open)
		{
			printToField("Open");
		}
		
		if(e.getSource() == clear)
		{
			textArea.setText("");
		}
		
		else if(e.getSource() == save)
		{
			printToField("Save");
		}
		
		else if(e.getSource() == search)
		{
			 try
			{
				clearField();
				textArea.setText("");
				String input = textField.getText();
				input = input.replaceAll(" ", "_").toLowerCase();
				printToField("https://en.wikipedia.org/wiki/" + input);
				URL my_url = new URL("https://en.wikipedia.org/wiki/" + input);
				
				Parser parse = new Parser(my_url);
            
				ArrayList<String> webPage = parse.get(my_url);
				int [] range = parse.getArray(webPage);
				for(int i = range[0]; i < range[1]; i++)
				{
					String tmp = parse.extract(webPage.get(i));
					if(tmp.length() > 1)
						printToField(tmp);
				}
			}

			catch (Exception ex) 
			{
			  System.out.println("Uh Oh! AN Error Occured!");
			}
		}
	}
}