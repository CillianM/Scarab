import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

class fileManager
{
	private String loc;
	fileManager()
	{
		loc = "";
	}

	fileManager(String loc)
	{
		this.loc = loc;
	}

	//creates/overwrites file with a name specified in the text field
	void save(String text)
	{
		File file = new File(loc);
		try
		{
			String saveType = "overwritten";
			boolean isNew = file.createNewFile();
			if(isNew)
				saveType = "created and saved";
			PrintWriter writer = new PrintWriter(file);
			writer.println(text);
			System.out.println(loc + " has been " + saveType);
			writer.close();
				
		}

		catch(IOException e)
		{
			System.out.println("Error creating file");
		}
	}

	void open(Scarab scarab)
	{
		try
			{
				File file = new File(loc);
				Scanner scan = new Scanner(file);
				System.out.println("reading file " + loc);
				while(scan.hasNextLine())
				{
					scarab.printToField(scan.nextLine());
				}
			}
			catch(FileNotFoundException f)
			{
				System.out.println("Error reading file " + loc);
			}
	}
}