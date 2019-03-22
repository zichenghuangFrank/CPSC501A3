package sender;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Client {
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket clientSocket = null;
		String host = "127.0.0.1";
		clientSocket = new Socket(host, 4545);
		
		System.out.println("The client has been set.");
		
		ObjectCreator myObj = new ObjectCreator();
		Object obj = myObj.creator();
		Serializer ser = new Serializer();
		Document doc = ser.serialize(obj);
		FileOutputStream fileName = null;
		
		try {
			fileName = new FileOutputStream("receiver.xml");
			XMLOutputter seri = new XMLOutputter();
			seri.output(doc, System.out);
			seri.output(doc, fileName);
		}catch (IOException e) {
			System.err.println(e);
		}
		
		byte[] bytes = new byte[24 * 1024];
		InputStream in = new FileInputStream("receiver.xml");
		OutputStream out = clientSocket.getOutputStream();
		
		int count;
		while((count = in.read(bytes)) > 0) {
			out.write(bytes, 0, count);
		}
		
		out.close();
		in.close();
		clientSocket.close();
	}
}
