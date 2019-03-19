package sender;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OnlyPrimitives implements utility{
	
	private boolean aBoolean;
	private byte aByte;
	private char aChar;
//	private short aShort;
	private int aInteger;
//	private long aLong;
//	private float aFloat;
//	private double aDouble;
	
	public OnlyPrimitives() {
		aBoolean = false;
		aByte = 0;
		aChar = '0';
		aInteger = 0;
	}
	
	public void setBoolean(boolean b) {
		aBoolean = b;
	}
	
	public void setByte(byte b) {
		aByte = b;
	}
	
	public void setChar(char c) {
		aChar = c;
	}
	
	public void setInt(int n) {
		aInteger = n;
	}
	
	public boolean getBoolean() {
		return aBoolean;
	}
	
	public byte getByte() {
		return aByte;
	}
	
	public char getChar() {
		return aChar;
	}
	
	public int getInt() {
		return aInteger;
	}
	
	public void setFieldVal() throws IOException {
		BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the value for the boolean: ");
		// need to change
		setBoolean(Boolean.parseBoolean(re.readLine()));
		System.out.println("The boolean has been set to: " + getBoolean());
		System.out.println("Enter the value for the byte: ");
		setByte(Byte.parseByte(re.readLine()));
		System.out.println("The byte has been set to: " + getByte());
		System.out.println("Enter the value for the char: ");
		setChar(re.readLine().charAt(0));
		System.out.println("The char has been set to: " + getChar());
		System.out.println("Enter the value for the int: ");
		setInt(Integer.parseInt(re.readLine()));
		System.out.println("The integer has been set to: " + getInt());
		re.close();
	}
}
