package receiver;
import java.util.*;
import java.lang.reflect.*;
import static java.lang.System.out;

public class Inspector {
	public Inspector(){}
	
	//-----------------------------------------------------------
    public void inspect(Object obj, boolean recursive){
    	
		Vector objectsToInspect = new Vector();
		Class ObjClass = obj.getClass();
		
		System.out.println("inside inspector: " + obj + " (recursive = "+recursive+")");
		
		//inspect the current class
		inspectFields(obj, ObjClass,objectsToInspect, recursive);
		
		if(recursive){// Recursive
		    inspectFieldClasses( obj, ObjClass, objectsToInspect, recursive);
		}
    }
    
    //-----------------------------------------------------------
    public void inspectFields(Object obj,Class ObjClass,Vector objectsToInspect, boolean recursive){
		printClassInfo(ObjClass);
		printMethodInfo(ObjClass);
		printConstructorInfo(ObjClass);
    	
		if(ObjClass.getDeclaredFields().length >= 1){
			Field[] fArray = ObjClass.getDeclaredFields();
			for(Field f : fArray){
				f.setAccessible(true);
				if(!f.getType().isPrimitive()){
					objectsToInspect.addElement(f);
				}
				try {
					printNonOrRecursive(obj, f, recursive);
				} catch (Exception e) {
					e.setStackTrace(null);
				}
			}
		}
	
		if(ObjClass.getSuperclass() != null) {
			out.println("===================== Inspecting Superclass ============================");
		    inspectFields(obj, ObjClass.getSuperclass() , objectsToInspect, recursive);
		    out.println("========================================================================");
		} 
		
		if (ObjClass.getInterfaces() != null) {
			Class[] interfaces = ObjClass.getInterfaces();
			
			for (Class interf : interfaces) {
				out.println("===================== Inspecting Interface =========================");
				inspectFields(obj, interf, objectsToInspect, recursive);
				out.println("====================================================================");
			}
		}
    }
    
    //-----------------------------------------------------------
    public void inspectFieldClasses(Object obj, Class ObjClass, Vector objectsToInspect, boolean recursive){
    	if(objectsToInspect.size() > 0) {
    		out.println("---- Inspecting Field Classes ----");
    	}
    	
    	Enumeration e = objectsToInspect.elements();
    	while(e.hasMoreElements()) {
    		Field f = (Field) e.nextElement();
    		out.println("Inspecting Field: " + f.getName());
    		
    		System.out.println("*********           start         *********");
    		try {
				
				inspect( f.get(obj) , recursive);
				
			    
    		} catch (Exception exp) {
    			out.println("The field is null");
    			
    		}
    		System.out.println("*********           end           *********");
    	}
    }
    
    // Class Information 
    public void printClassInfo(Class cls) {
    	out.println();
    	out.println("================= Basic ClassInfo =================");
    	
    	out.println("Class: " + cls.getName());
    	out.println("Immediate superclass: " + cls.getSuperclass());
    	out.println("Is Interface: " + cls.isInterface());
    	out.print("Interfaces: ");
    	Class[] interArray = cls.getInterfaces();
    	arrayInfo(interArray);
    	out.println("============== Basic ClassInfo End ================");
    	out.println();
    }
    
    // Method Information 
    public void printMethodInfo(Class cls) {
    	out.println();
    	out.println("================= Method Info =====================");
    	
    	Method[] mArray = cls.getDeclaredMethods();
    	
    	for(Method m : mArray) {
    		out.println("----Method name: " + m.getName());
    		
    		out.print("----Exceptions throw: ");
    		Class[] excArray = m.getExceptionTypes();
    		arrayInfo(excArray);
    		
    		out.print("----Parameter types: ");
    		Class[] parArray = m.getParameterTypes();
    		arrayInfo(parArray);
    	
    		out.println("----Return type: " + m.getReturnType());
    		out.println("----Modifier: " + Modifier.toString(m.getModifiers()));
    		out.println();
    	}
    	out.println("=============== Method Info End ====================");
    	out.println();
    }
    
    // Constructor Information 
    public void printConstructorInfo(Class cls) {
    	out.println();
    	out.println("================= Constructor Info =================");
    	Constructor[] conArray = cls.getConstructors();
    	for(Constructor c : conArray) {
    		out.print("----Parameter types: ");
    		Class[] parArray = c.getParameterTypes();
    		arrayInfo(parArray);
    		
    		out.println("----Modifier: " + Modifier.toString(c.getModifiers()));
    	}
    	out.println("=============== Constructor Info End ===============");
    	out.println();
    }
    
    // Check the field style (array or not) 
    public void printNonOrRecursive(Object obj, Field f, boolean recursive) throws IllegalArgumentException, IllegalAccessException {
    	if(f.getType().isArray()) {
    		out.println("=========== The field is an array =============");
    		f.setAccessible(true);
    		out.println("----Field(array) name: " + f.getName());
    		out.println("----Field(array) Component type: " + f.get(obj).getClass().getComponentType());
    		out.println("----Field(array) length: " + Array.getLength(f.get(obj)));
    		out.println("----Modifier: " + Modifier.toString(f.getModifiers()));
    		if(f.get(obj).getClass().getComponentType().isArray()) {
    			for(int i = 0; i < Array.getLength(f.get(obj)); i++) {
    				out.print("No." + i + "= ");
    				arrayRecursiveMethod(Array.get(f.get(obj), i));
    				out.println();
    			}
    		}else if (!f.getType().isPrimitive()){
    			for(int i = 0; i < Array.getLength(f.get(obj)); i++) {
    				out.println("No." + i + "= " + Array.get(f.get(obj), i));
    			}
    			if(recursive == false) {
    				out.println("Identity Hash code: " + System.identityHashCode(obj));
    			}
    		}
    		out.println("================ The array end ===============");
    	} else {
    		out.println();
    		out.println("=================== Field Info ================");
    		out.println("----Field name: " + f.getName());
    		out.println("----Field type: " + f.getType());
    		if(!f.getType().isPrimitive() && recursive == false) {
    			out.println("----Value: " + f.get(obj) + " " + System.identityHashCode(obj));
    		}else {
    			out.println("----Value: " + f.get(obj));
    		}
    		out.println("----Modifier: " + Modifier.toString(f.getModifiers()));
    		out.println("================ Field Info End ==================");
    		out.println();
    	}
    }
    
    // Array recursive helper function 
    public void arrayRecursiveMethod(Object obj) {
    	out.println("----Array name: " + obj.getClass().getName());
    	out.println("----Array Component type: " + obj.getClass().getComponentType());
    	out.println("----Array length: " + Array.getLength(obj));
    	if(obj.getClass().getComponentType().isArray()) {
    		for(int i = 0; i < Array.getLength(obj); i++) {
				out.println("No." + i + "= ");
				arrayRecursiveMethod(Array.get(obj, i));
				out.println();
			}
    	}else {
    		for(int i = 0; i < Array.getLength(obj); i++) {
				out.println("No." + i + "= " + Array.get(obj, i));
			}
    	}
    }
       
    // Helper array function
    public void arrayInfo(Class[] array) {
    	out.print("{");
    	if(array.length == 0) {
    		out.print("null");
    	}else {
	    	for(int i = 0; i < array.length; i++) {
	    		out.print(array[i].getName());
	    		if(i == array.length - 1) {
	    			out.print(" ");
	    		}else {
	    			out.print(",");
	    		}
	    	}
    	}
    	out.println("}");
    }
}


