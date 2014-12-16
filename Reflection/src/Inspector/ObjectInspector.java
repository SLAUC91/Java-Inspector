package Inspector;

import java.lang.reflect.*;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ObjectInspector
{
    public ObjectInspector() { }
    
    public void inspect(Object obj, boolean recursive)
    {
	Vector objectsToInspect = new Vector();
	Class ObjClass = obj.getClass();

	System.out.println("inside inspector: " + obj + " (recursive = "+recursive+")");
        
        //Dedicated class of current class
        DeclatedClass(ObjClass);        
	
        //Get superclass 
        GetSuper(ObjClass);
        
        //Get Interfaces
        GetInterface(ObjClass);
        
        System.out.println("\n--------------- " + "Constructors for: " + ObjClass.getName() + " ---------------"); 
        
        getConstructor(ObjClass);
        
        System.out.println("\n--------------- " + "Methods for: " + ObjClass.getName() + " ---------------");
        
        getMethods(ObjClass);
        
        System.out.println("\n--------------- " +  "Fields for: " + ObjClass.getName() + " ---------------");
  
        //inspect the current class
        getFields(obj, ObjClass,objectsToInspect);
        if(recursive)
			inspectFieldClasses( obj, ObjClass, objectsToInspect, recursive);
	   
    }
    
    private void DeclatedClass (Class ObjClass){
        Class[] classes = ObjClass.getDeclaredClasses();
        for (int i = 0; i < classes.length; i++)
        {
            System.out.println("Declared Class: " +
            classes[i].getName());
        }
    
    }   
    
    private Class GetImmediateSuper(Class ObjClass) {
        Class superclass = ObjClass.getSuperclass();
        return superclass;
    }
    
    private void GetSuper(Class ObjClass){
        System.out.println("Immediate Superclass: " + GetImmediateSuper(ObjClass).toString());
    }
    
    private void GetInterface(Class ObjClass) {
        Class[] inter = ObjClass.getInterfaces();
        for (int i = 0; i < inter.length; i++)
            {
                System.out.println("Interfaces found: " +
                    inter[i].getName());
            }
    }
    
    private void getConstructor(Class ObjClass) {
        Constructor[] con = ObjClass.getDeclaredConstructors();

        for (int i = 0; i < con.length; i++){
            //Return the constructor info
            System.out.println("Constuctors Declared: " + con[i].toGenericString());
            
            //Modifier
            getModifier( Modifier.toString(con[i].getModifiers()) );
            
            //Get the parameter info
            Type[] par = con[i].getParameterTypes();
            for (int j = 0; j < par.length; j++){
                System.out.println("\t" + "ParameterType" + "[" + j + "]" + ": " + par[j]);
            }       
        }
        
        //Get the inheritance hierarchy of all constructors
        getConstructorHierarchy(ObjClass);
        
    }
    
    private void getConstructorHierarchy(Class ObjClass){
        Class A = GetImmediateSuper(ObjClass);
        if ( A != Object.class){
            System.out.println("\n"+ "++++++++++ " + "Super Constructors from: "+ A.getName() +" ++++++++++");
            getConstructor(A);
        }
    }
    
    private void getMethods(Class ObjClass) {
        Method[] m = ObjClass.getDeclaredMethods();
        
        for (int i = 0; i < m.length; i++){
            System.out.println("Methods Declared: " + m[i].toGenericString());
            //System.out.println("Methods Declared: " + m[i].getName());
            
            //Modifier
            getModifier( Modifier.toString(m[i].getModifiers()) );
            
            //Return type
            Class ret_type = m[i].getReturnType();
            System.out.println("\t" + "Return Type: " + ret_type);
            
            //exceptions thrown
            Type[] exp = m[i].getGenericExceptionTypes();
            for (int x = 0; x < exp.length; x++){
                System.out.println("\t" + "Exception Types: " + exp[x]);
            }
            
            
            Type[] par = m[i].getParameterTypes();
            for (int j = 0; j < par.length; j++){
                System.out.println("\t" + "ParameterType" + "[" + j + "]" + ": " + par[j]);
            }
                        
        }
        
        getMethodHierarchy(ObjClass);
        
    }
    
    private void getMethodHierarchy(Class ObjClass){
        Class A = GetImmediateSuper(ObjClass);
        if ( A != Object.class){
            System.out.println("\n"+ "++++++++++ " + "Inherited Methods from: "+ A.getName() +" ++++++++++");
            getMethods(A);
        }
    }
   
    private void inspectFieldClasses(Object obj,Class ObjClass,
				     Vector objectsToInspect, boolean recursive)
    {
	
	if(objectsToInspect.size() > 0 )
	    System.out.println("---- Inspecting Field Classes ----");
	
	Enumeration e = objectsToInspect.elements();
	while(e.hasMoreElements())
	    {
		Field f = (Field) e.nextElement();
		System.out.println("Inspecting Field: " + f.getName() );       
		
		try
		    {
			System.out.println("******************");
                            inspect( f.get(obj) , recursive);
                        
                            if (f.getType().isArray()){
                                Class cType = f.getType().getComponentType();
                                try {
                                    Object array = f.get(obj);
                                    int length = Array.getLength(array);
                                    for (int i = 0; i < length; i++)
                                    {
                                        Object elem = Array.get(array, i);
                                        
                                        //inspect(elem, recursive);
                                        System.out.println(elem);

                                    }

                                } catch (IllegalArgumentException ex) {
                                    Logger.getLogger(ObjectInspector.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IllegalAccessException ex) {
                                    Logger.getLogger(ObjectInspector.class.getName()).log(Level.SEVERE, null, ex);
                                }                
                                }
                            
                            else{
                                //inspect( f.get(obj) , recursive);
                            }
                        
			System.out.println("******************");
		    }
		catch(Exception exp) { exp.printStackTrace(); }
	    }
    }
       
    private void getFields(Object obj,Class ObjClass, Vector objectsToInspect)
    {
	
	if(ObjClass.getDeclaredFields().length >= 1)
	    {
		Field[] f = ObjClass.getDeclaredFields();
                for (int i = 0; i < f.length; i++){
                    f[i].setAccessible(true);
                    
                    if(! f[i].getType().isPrimitive() &&  f[i].getType() != java.lang.String.class && f[i].getType() != java.lang.Integer.class) 
                        objectsToInspect.addElement( f[i] );
                
                    Type par = f[i].getGenericType();

                    try
                        {
                            System.out.println("Field: " + "\t" + f[i].getName() + " = " + f[i].get(obj));

                            getModifier( Modifier.toString(f[i].getModifiers()) );

                            System.out.println("\t" + "Type: " + par.toString());
                        }
                    catch(Exception e) {}
                    
                }
            
		
            }
            if(ObjClass.getSuperclass() != java.lang.Object.class)
                getFields(obj, ObjClass.getSuperclass() , objectsToInspect); 
            
    }
        
    //Get Modifier
    public void getModifier(String modifier_string){
        
        if (!"".equals(modifier_string)){
            System.out.println("\t" + "Modifier: " + modifier_string); }
        else {
            System.out.println("\t" + "Modifier: " + "Default");
        }
    
    }
    
}