package sender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ArrayPrimitives implements utility {
	
	public int[] aIntArray;
	private int length;
	
	public ArrayPrimitives() {
		System.out.println("Please enter the length for the array: ");

	}

	public void setFieldVal() throws IOException {
		Scanner in = new Scanner(System.in);
		length = in.nextInt();
		aIntArray = new int[length];
		BufferedReader re = new BufferedReader(new InputStreamReader(System.in));
		for(int i = 0; i < aIntArray.length; i++) {
			System.out.println("Please set int value for index[" + i + "]");
			aIntArray[i] = Integer.parseInt(re.readLine());
		}
		System.out.print("The user input array is {");
		for(int j = 0; j < aIntArray.length; j++) {
			System.out.print(aIntArray[j]);
			if(j == aIntArray.length - 1) {
				System.out.print("");
			}else {
				System.out.print(",");
			}
		}
		System.out.println("}");
		in.close();
		re.close();	
	}
	
}
