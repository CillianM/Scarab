//simple call to get the programme going, shouldn't actuall incude anything other than the instantiation of the scarab method
public class  Call
{	
	public static void main(String [] args)
	{
		Scarab scarab = new Scarab();
		scarab.getInstantiation(scarab); //save instatiation of current object to be used across classes
	}
}