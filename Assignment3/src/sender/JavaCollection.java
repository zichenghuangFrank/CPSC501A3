package sender;

import java.io.IOException;
import java.util.ArrayList;

public class JavaCollection implements utility{

	private ArrayList<Object> objectList = new ArrayList<Object>();    
	
	public JavaCollection() {
		objectList.add(new OnlyPrimitives());
		objectList.add(new ReferencesObject());
	}
	
	public void setFieldVal() throws IOException {}

}
