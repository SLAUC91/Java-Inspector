package Networking;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	private int portToListen = 12324;

	public Server(int port)
	{
		this.portToListen = port;
	}

	public void sendFile(String fileName) throws IOException {
		ServerSocket servsock = new ServerSocket(this.portToListen);

		System.out.println("Accepting a connection...");
		// the server thread is blocked until a connection from a client arrives
		Socket clientSock = servsock.accept();

		System.out.println("Connected to a client, " + clientSock.getInetAddress().toString());
		
		// reading file, storing it into a buffer
		File myFile = new File(fileName);
		byte[] buffer = new byte[(int) myFile.length()];
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
		bis.read(buffer, 0, buffer.length);
		
		// sending the file over network
		OutputStream os = clientSock.getOutputStream();
		os.write(buffer, 0, buffer.length);
		os.flush();
		clientSock.close();
		
		System.out.println("File " + fileName + " sent, connection closed!");

	}

}

