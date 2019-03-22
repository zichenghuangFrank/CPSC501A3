package receiver;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;

import org.jdom2.Element;

public class Deserializer {
	public static HashMap<Integer,Object> hashMap = new HashMap<Integer,Object>();
	
	public Object deserialize(org.jdom2.Document document) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		
		Element root = document.getRootElement();
		
		List<Element> children = root.getChildren();
		
		int num = 0;
		for(Element e : children) {
			if(e.getAttribute("length") == null) {
				Class clsObj = Class.forName(e.getAttributeValue("class"));
				Constructor c = clsObj.getConstructor();
				Object obj = c.newInstance();
				hashMap.put(Integer.parseInt(e.getAttributeValue("id")), obj);
			}else {
				Class clsObj = Class.forName(e.getAttributeValue("class"));
				int arrayLength = Integer.parseInt(e.getAttributeValue("length"));
				Object obj = Array.newInstance(clsObj.getComponentType(), arrayLength);
				hashMap.put(Integer.parseInt(e.getAttributeValue("id")), obj);
			}
		}
		
		for(Element e1 : children) {
			if(e1.getAttribute("length") == null) {
				assignFieldVal(e1, hashMap.get(num));                // non-Array
			}else {
				assignArrayVal(e1, hashMap.get(num));				 // Array
			}
			num++;
		}
		
		return hashMap.get(0);
		
	}
	
	
	public void assignFieldVal(Element e, Object obj) throws ClassNotFoundException, NoSuchFieldException, SecurityException, NumberFormatException, IllegalArgumentException, IllegalAccessException {
		List<Element> assField = e.getChildren();
		for(Element ef : assField) {
			String fClass = ef.getAttributeValue("declaringclass");
			Class clsObj = Class.forName(fClass);
			String fName = ef.getAttributeValue("name");
			Field f = clsObj.getDeclaredField(fName);
			f.setAccessible(true);
			if(Modifier.isFinal(f.getModifiers())) {
				
			}else if(f.getType().isPrimitive()) {
				String value = ef.getChild("value").getText();
				unWrappedPrimitivesVal(f, obj, value);
			}else {
				String value = ef.getChild("reference").getText();
				f.set(obj, hashMap.get(Integer.parseInt(value)));
			}
		}
	}
	
	public void assignArrayVal(Element e, Object obj) throws NumberFormatException{
		List<Element> arrList = e.getChildren();
		
		int num = 0;
		for(Element ea : arrList) {
			if(obj.getClass().getComponentType().isPrimitive()) {
				String value = ea.getText();
				unWrappedPrimitivesArrayVal(obj, num, value);
			}else {
				String value = ea.getText();
				if(!value.equals("null")) {
					Array.set(obj, num, hashMap.get(Integer.parseInt(value)));
				}else {
					Array.set(obj, num, null);
				}
			}
			num++;
		}
	}
	
	public void unWrappedPrimitivesArrayVal(Object obj, int num, String val) {
		if(obj.getClass().getComponentType() == int.class) {
			Array.set(obj, num, new Integer(Integer.parseInt(val)));
		}else if(obj.getClass().getComponentType() == boolean.class) {
			Array.set(obj, num, new Boolean(Boolean.parseBoolean(val)));
		}else if(obj.getClass().getComponentType() == char.class) {
			Array.set(obj, num, new Character(val.charAt(0)));
		}else if(obj.getClass().getComponentType() == short.class) {
			Array.set(obj, num, new Short(Short.parseShort(val)));
		}else if(obj.getClass().getComponentType() == long.class) {
			Array.set(obj, num, new Long(Long.parseLong(val)));
		}else if(obj.getClass().getComponentType() == byte.class) {
			Array.set(obj, num, new Byte(Byte.parseByte(val)));
		}else if(obj.getClass().getComponentType() == float.class) {
			Array.set(obj, num, new Float(Float.parseFloat(val)));
		}else if(obj.getClass().getComponentType() == double.class) {
			Array.set(obj, num, new Double(Double.parseDouble(val)));
		}
	}
	
	public void unWrappedPrimitivesVal(Field f, Object obj, String val) throws NumberFormatException, IllegalArgumentException, IllegalAccessException {
		if(f.getType() == int.class) {
			f.set(obj, new Integer(Integer.parseInt(val)));
		}else if(f.getType() == boolean.class) {
			f.set(obj, new Boolean(Boolean.parseBoolean(val)));
		}else if(f.getType() == char.class) {
			f.set(obj, new Character(val.charAt(0)));
		}else if(f.getType() == short.class) {
			f.set(obj, new Short(Short.parseShort(val)));
		}else if(f.getType() == long.class) {
			f.set(obj, new Long(Long.parseLong(val)));
		}else if(f.getType() == byte.class) {
			f.set(obj, new Byte(Byte.parseByte(val)));
		}else if(f.getType() == float.class) {
			f.set(obj, new Float(Float.parseFloat(val)));
		}else if(f.getType() == double.class) {
			f.set(obj, new Double(Double.parseDouble(val)));
		}
	}
	
}

