import Networking.Server;
import Objects.*;
import java.io.FileWriter;
import java.util.Scanner;
import org.jdom2.Document;

public class Servermain {
    
	//Sample Obj - Car
    private static Car setCar (Scanner input) {
        System.out.println("Car Object");
        Car A = new Car();
        System.out.print("Name <String>: ");
        A.Name = input.next();
        System.out.print("Year <Int>: ");
        A.Year = input.nextInt();  
        return A;
    }
    
	//Sample Obj - Parking
    private static Parking setParking (Scanner input, Car A) {
        System.out.println("Parking Object");
        System.out.print("Lot Number <Int>: ");
        int lotnum = input.nextInt();
        System.out.print("City <String>: ");
        String city = input.next();  
        Parking B = new Parking(A, lotnum, city);
        return B;
    }
    
    private static void Menu(){
        System.out.println("Which type of object would you like to create: ");
        System.out.println("1. Simple object");
        System.out.println("2. Object that contains references to other objects");
        System.out.println("3. Object that contains an array of primitives");
        System.out.println("4. Object that contains an array of object references");
        System.out.println("5. Java’s collection classes");
        System.out.print("Choice: ");
    }
    
	//Serialize Obj
    public static void Serial(Object A){
        try {
            Document doc = SerDeser.serializeObject(A);
            MyXMLHandler.writeXML(doc, new FileWriter("Output.xml"));
			
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	//send an obj to file
    public static void SendObj (){
        Server server  = new Server(1236);
        try
		{
		server.sendFile("Output.xml");
		}
		catch(Exception exp)
		{
		System.err.println(exp.toString());
		}
    }
    
    private static void Servermain(){
       Object curObj = null;
        Scanner input = new Scanner(System.in);
        int choice = 0;
        
        //Menu
        Menu();
        
        if ( input.hasNextInt() ){
            choice = input.nextInt();

            if (choice == 1){
                
                curObj = setCar (input);
                
            }
            else if (choice == 2) {

                Car A = setCar (input);
                curObj = setParking(input, A);
                
            }
            else if (choice == 3) {
                
                PrimArray A = new PrimArray();
                for (int i = 0; i < A.aint.length; i++){
                    System.out.print("Array[" + i + "] <Int>: ");
                    A.aint[i] = input.nextInt();
                }
                
                for (int j = 0; j < A.astring.length; j++){
                    System.out.print("Array2[" + j + "] <String>: ");
                    A.astring[j] = input.next();
                }
                
                curObj = A;

            }
            
            else if (choice == 4) {
               NPrimArray A = new NPrimArray();
               curObj = A;
            }
            else if (choice == 5) {
                Collect list = new Collect();
                list.setList(2);
                curObj = list;
            }
            
            else {}

        }
        
        else { System.out.println("Invalid Input"); } 
        
        Serial(curObj);
        
        SendObj();
    }
    
    public static void main(String[] args) {
        Servermain();
    }
    
}
