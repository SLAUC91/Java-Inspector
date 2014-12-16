import Inspector.ObjectInspector;
import Networking.Client;

public class Clientmain {

	//Recieve Obj
    public static void RecObj(){
		//Client address
        Client client = new Client(1236, "127.0.0.1");
        
	try
	{
		client.receiveFile("Client_Copy_Of_Output.xml");
	}
	catch(Exception exp)
	{
		System.err.println(exp.toString());
	}
    }
    
	//Deserialize File
    public static Object DeSerial(String file) {
        try {
            Object curObj = SerDeser.deserializeObject(MyXMLHandler.readFromFile(file));
            return curObj;
        } catch (Exception ex) {
            System.err.println(ex.toString());
        }
        return null;
    }
    
	//Client main connect
    private static void Clientmain(){        
        RecObj();
        Object curObj =  DeSerial("Client_Copy_Of_Output.xml");      
        
        ObjectInspector init = new ObjectInspector();
        init.inspect(curObj, true);
    }
    
    public static void main(String[] args) {
          Clientmain();      
    }

    
}
