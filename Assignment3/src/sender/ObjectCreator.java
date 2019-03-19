package sender;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ObjectCreator {
	public Object creator() throws IOException{
		BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
		menuPrint();
		
		utility object = null;
		String userInput = re.readLine();
		
		switch(userInput) {
		case "1":
			object = new OnlyPrimitives();
			System.out.println("The object with only primitives is created.");
			break;
		
		case "2":
			object = new ReferencesObject();
			System.out.println("The object with references to object is created.");
			break;
			
		case "3":
			object = new ArrayPrimitives();
			System.out.println("The object that contains an array of primitives is created.");
			break;
			
		case "4":
			object = new ArrayObjectReferences();
			System.out.println("The object that contains an array of object references is created.");
			break;
			
		case "5":
			object = new JavaCollection();
			System.out.println("The object that uses an an instance of one of Java's collection is created.");
			break;
			
		default:
			System.out.println("User does not make a choice.");
		}
		
		
		object.setFieldVal();
		re.close();
		return object;
		
	}
	
	public void menuPrint() {
		System.out.println("1. A simple object with only primitives for instance variables.");
		System.out.println("2. An object that contains references to other objects.");
		System.out.println("3. An object that contains an array of primitives.");
		System.out.println("4. An object that contains an array of object references.");
		System.out.println("5. An object that uses an instance of one of Java's collection.");
	}
}
