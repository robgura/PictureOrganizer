
public class CameraGroupNotFound extends Exception
{

	public CameraGroupNotFound(String _groupName)
	{
		groupName = _groupName;
	}
	
	public String groupName;

}
