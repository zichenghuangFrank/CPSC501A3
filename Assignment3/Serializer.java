package sender;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Serializer {
	
	private static int num = 0;
	private static IdentityHashMap<Object, Integer> objMap = new IdentityHashMap<Object, Integer>();
	private Element root;
	
	public org.jdom2.Document serialize(Object obj){
		root = new Element("serialized");
		recObjectXML(obj);
		return new Document(root);
	} 
	
	public void recObjectXML(Object obj) {
		int id = 0;
		if(!objMap.containsKey(obj)) {
			int mapId = num;
			objMap.put(obj, mapId);
			num++;
			Class cls = obj.getClass();
			Element child = new Element("object");
			root.addContent(child);
			child.setAttribute("class", cls.getName());
			child.setAttribute("id", Integer.toString(mapId));
			
			if(cls.getDeclaredFields().length > 0){
				fieldXML(obj, child);
			}
		}
	}
	
	public void fieldXML(Object obj, Element parent) {
		
		Field[] fields = obj.getClass().getDeclaredFields();
		
		for(Field f : fields) {
			f.setAccessible(true);
			Element fieldTag = new Element("field");
			fieldTag.setAttribute("name", f.getName());
			fieldTag.setAttribute("declaringclass", f.getDeclaringClass().getName());
			
			if(f.getType().isPrimitive()) {
				try {
					valueTag(fieldTag, String.valueOf(f.get(obj)));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				} 
			} else if(f.getType().isArray()) {
				try {
					if(f.get(obj) != null) {
						arrayXML(f.get(obj));
						referenceTag(fieldTag, Integer.toString(objMap.get(f.get(obj))));
					}else {
						referenceTag(fieldTag, "null");
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				} 
			}else{
				try {
					if(f.get(obj) != null){
						recObjectXML(f.get(obj));
						referenceTag(fieldTag, Integer.toString(objMap.get(f.get(obj))));
					}else{
						referenceTag(fieldTag, "null");
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			
			parent.addContent(fieldTag);
		}
	}
	
	public void arrayXML(Object obj) {
		if(!objMap.containsKey(obj)) {
			int mapId = num;
			objMap.put(obj, mapId);
			num++;
			Class cls = obj.getClass();
			Element child = new Element("object");
			root.addContent(child);
			child.setAttribute("class", cls.getName());
			child.setAttribute("id", Integer.toString(mapId));
			child.setAttribute("length", Integer.toString(Array.getLength(obj)));
			if(cls.getComponentType().isPrimitive()) {
				for(int i = 0; i < Array.getLength(obj); i++) {
					valueTag(child, Array.get(obj, i).toString());
				}
			}else {
				for(int i = 0; i < Array.getLength(obj); i++) {
					if(Array.get(obj, i) != null) {
						recObjectXML(Array.get(obj, i));
						referenceTag(child, Integer.toString(objMap.get(Array.get(obj, i))));
					}else{
						referenceTag(child, "null");
					}
				}
			}
		}
	}
	
	public void valueTag(Element parent, String value){
		Element valTag = new Element("value");
		valTag.setText(value);
		parent.addContent(valTag);
	}
	
	public void referenceTag(Element parent, String value){
		Element valTag = new Element("reference");
		valTag.setText(value);
		parent.addContent(valTag);
	}
	
	public void xmlTest(Document doc){
		// serialize with two space indents and extra line breaks
		try {
			XMLOutputter serializerr = new XMLOutputter();
			serializerr.output(doc, System.out);				
			serializerr.output(doc, new FileOutputStream("receiver1.xml"));	
			System.out.println("success");
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
