package receiver;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Server {
	public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(4545);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
        }

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            socket = serverSocket.accept();
        } catch (IOException ex) {
            System.out.println("Can't accept client connection. ");
        }

        try {
            in = socket.getInputStream();
        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
        }

        try {
            out = new FileOutputStream("receiver1.xml");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found.");
        }

        byte[] bytes = new byte[16*1024];

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }
        
        System.out.println("Success");
        
        SAXBuilder builder = new SAXBuilder();
        
        try {
			Document doc = builder.build("receiver1.xml");
			System.out.println("Begin to deserialize!");
		        
		    Deserializer deser = new Deserializer();
		    Object deObj = deser.deserialize(doc);
		   
		    Inspector in1 = new Inspector();
		    in1.inspect(deObj, true);
		    
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     
        
        out.close();
        in.close();
        socket.close();
        serverSocket.close();
    }
}
