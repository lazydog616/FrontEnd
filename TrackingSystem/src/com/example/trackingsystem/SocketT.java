package com.example.trackingsystem;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.IllegalBlockingModeException;

import android.util.Log;
public class SocketT {
	Socket soc;
	String hostname;
	int port;
	ServerSocket serverSocket = null;
	DataInputStream dataInputStream = null;
	DataOutputStream dataOutputStream = null;
	SocketT() 
	{
		
		//hostname =  "158.130.108.158";
		hostname = "localhost";
		port = 8080;
		
		soc =  new Socket();
	}
	void get() 
	{
		try {
			  soc = new Socket(hostname, 8008);
			  System.out.println("test");
			  dataOutputStream = new DataOutputStream(soc.getOutputStream());
			  dataInputStream = new DataInputStream(soc.getInputStream());
			  System.out.print("test");
			  dataOutputStream.writeUTF("Hello!");
			  System.out.println("ip: " + soc.getInetAddress());
			   System.out.println("message: " + dataInputStream.readUTF());
			  //textIn.setText(dataInputStream.readUTF());
			 } catch (UnknownHostException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			 } catch (Exception e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			 }
			 finally{
			  if (soc != null){
			   try {
			    soc.close();
			   } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			   }
			  }

			  if (dataOutputStream != null){
			   try {
			    dataOutputStream.close();
			   } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			   }
			  }

			  if (dataInputStream != null){
			   try {
			    dataInputStream.close();
			   } catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			   }
			  }
			 }
			}
	};
	




