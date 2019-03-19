package sender;

import java.io.IOException;
import java.util.Scanner;

public class ArrayObjectReferences implements utility{
	Scanner in = new Scanner(System.in);
	
	private OnlyPrimitives[] anObjectArray;
	private int length;
	
	public ArrayObjectReferences() {
		System.out.println("Please enter the length for the array: ");
		length = in.nextInt();
		anObjectArray = new OnlyPrimitives[length];
	}

	public void setFieldVal() throws IOException {}
}
