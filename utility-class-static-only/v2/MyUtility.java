public class MyUtility
{
	//adding a private constructor to ensure that no insatnces can be created
	private MyUtility() throws InstantiationException
	{
	    throw new InstantiationException("Instances of this type are forbidden.");
	}
}
