package sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ArrayObjectReferences implements utility{
	
	public OnlyPrimitives[] anObjectArray;
	//private int length;
	
	public ArrayObjectReferences() {
		
		//System.out.println("Please enter the length for the array: ");
		//length = in.nextInt();
		//anObjectArray = new OnlyPrimitives[length];
		anObjectArray = new OnlyPrimitives[3];
		anObjectArray[0] = new OnlyPrimitives();
		anObjectArray[1] = new OnlyPrimitives();
		anObjectArray[2] = new OnlyPrimitives();
	}

	public void setFieldVal() throws IOException {}
}
