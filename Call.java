//simple call to get the programme going
public class  Call
{	
	public static void main(String [] args)
	{
		Scarab scarab = new Scarab();
		System.out.println(scarab.version());
		scarab.setVisible(true);
	}
}