package Networking;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
	
	int portToConnect = 12324;
	String serverAddress = "127.0.0.1";
	
	public Client(int port, String serverAddr)
	{
		this.portToConnect = port;
		this.serverAddress = serverAddr;
	}
	
  public void receiveFile(String pathToStore) throws Exception {
    Socket sock = new Socket(this.serverAddress, this.portToConnect);
       
    byte[] buffer = new byte[102000004];
          
    System.out.println("Starting to read from server");
    InputStream is = sock.getInputStream();
    int bytesRead = is.read(buffer, 0, buffer.length);
    
    // dealing with creation of a file on the client side
    FileOutputStream fos = new FileOutputStream(pathToStore);
    BufferedOutputStream bos = new BufferedOutputStream(fos);

    
    bos.write(buffer, 0, bytesRead);
    bos.close();
    sock.close();
    System.out.println("File received, connection closed");
  }
}
