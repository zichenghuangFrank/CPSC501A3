package sender;

import java.io.IOException;

import org.jdom2.Document;

public class Test {
	public static void main(String [] args) throws IOException {
		
		ObjectCreator obj = new ObjectCreator();
		Object myObj = obj.creator();
		Serializer s = new Serializer();
		Document doc = s.serialize(myObj);
		s.xmlTest(doc);
	}
	
	public static void menuPrint() {
		System.out.println("1. A simple object with only primitives for instance variables.");
		System.out.println("2. An object that contains references to other objects.");
		System.out.println("3. An object that contains an array of primitives.");
		System.out.println("4. An object that contains an array of object references.");
		System.out.println("5. An object that uses an instance of one of Java's collection.");
	}
}
