package sender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class JavaCollection implements utility{    
	
	public Collection<Object> classList;
	
	// Initialize 4 elements in the arraylist
	public JavaCollection() {
		classList = new ArrayList<Object>();
		classList.add(new OnlyPrimitives());
		classList.add(new OnlyPrimitives());
		classList.add(new OnlyPrimitives());
		classList.add(new OnlyPrimitives());
	}
	
	public void setFieldVal() throws IOException {}

}
