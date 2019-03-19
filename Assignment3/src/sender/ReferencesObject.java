package sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReferencesObject implements utility{

	private char aChar;
	private int aInt;
	public OnlyPrimitives anObject;
	
	public ReferencesObject(){
		aChar = '0';
		aInt = 0;
		anObject = new OnlyPrimitives();
	}
	
	public void setChar(char c) {
		aChar = c;
	}
	
	public void setInt(int n) {
		aInt = n;
	}
	
	public char getChar() {
		return aChar;
	}
	
	public int getInt() {
		return aInt;
	}
	
	public void setFieldVal() throws IOException {
		BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the value for the char: ");
		setChar(re.readLine().charAt(0));
		System.out.println("The char has been set to: " + getChar());
		System.out.println("Enter the value for the int: ");
		setInt(Integer.parseInt(re.readLine()));
		System.out.println("The integer has been set to: " + getInt());
		System.out.println("Initialize the object values: ");
		anObject.setFieldVal();
		re.close();
	}
	
}
