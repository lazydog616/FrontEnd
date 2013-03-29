package com.example.trackingsystem;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.mapdemo.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class TrackingSystem extends FragmentActivity{
   PrintWriter out = null;
   BufferedReader in = null;
   ArrayList<Chunk> Info = null;
   Socket echoSocket = null;
   String Hostname = "158.130.106.158";
   int Port = 8008;
   //communication command
   String command = "Request";
   private GoogleMap mMap;
   @Override
   public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tracking_system);
    setUpMapIfNeeded();
  
   }
   @Override
   protected void onResume() {
       super.onResume();
       setUpMapIfNeeded();
   }
   @Override
   protected void onStop() {
    super.onStop();
    
   }
   ////Button OnClick
   public void Request(View v)
   {
	   command = "Request";
	   Log.i("command:",command);
	   new SocThread().execute(4);
	   if(Info == null) return;
	   else if(Info.size()>0) mMap.clear();
	   for(int i = 0;i<Info.size();i++)
	   {
		   Info.get(i).PrintChunk();
		   mMap.addMarker(new MarkerOptions().position(new LatLng(Info.get(i).lat, Info.get(i).lon)).title(Info.get(i).getTemAndAcce()));
		   
	   }
	   
   }
   public void Set(View v)
   {
	   command = "Set";
	   Log.i("command:",command);
	   new SocThread().execute(3);
	   
	   if(Info ==  null) return;
	   else if(Info.size() == 1)
	   {
		   Info.get(0).PrintCommand();
	   }
	   else {
		   Log.i("error:", "Set error");
	   }
	   
   }
   public void Off(View v)
   {
	   command = "Off";
	   Log.i("command:",command);
	   new SocThread().execute(1);
	   if(Info == null) return;
	   else if(Info.size() == 1)
	   {
		   Info.get(0).PrintCommand();
	   }
	   else {
		   Log.i("error:", "Off error");
	   }
   }
   public void Open(View v)
   {
	   command = "Open";
	   Log.i("command:",command);
	   new SocThread().execute(2);
	   if(Info == null) return;
	   else if(Info.size() == 1)
	   {
		   Info.get(0).PrintCommand();
	   }
	   else {
		   Log.i("error:", "Off error");
	   }
   }
   
   /////
   private void setUpMapIfNeeded() {
       // Do a null check to confirm that we have not already instantiated the map.
       if (mMap == null) {
           // Try to obtain the map from the SupportMapFragment.
           mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                   .getMap();
           // Check if we were successful in obtaining the map.
           if (mMap != null) {
               setUpMap();
           }
       }
   }
   private void setUpMap() {
       mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
   }
   void SendByte(byte cmd)
   {
	   try {
			
			echoSocket = new Socket(Hostname,Port);
			DataOutputStream dos = new DataOutputStream(echoSocket.getOutputStream());
			Byte test = 'r';
			echoSocket.getOutputStream().write(test);
			Log.i("command:","sent r");
			//echoSocket.getOutputStream().write(cmd);
			echoSocket.close();
		} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   }
   int ReceiveInt()
   {
	   int ch = 0;
	   try {
			
			echoSocket = new Socket(Hostname,Port);
			Log.i("command:","test555");
			
			DataInputStream dis = new DataInputStream(echoSocket.getInputStream());

			ch = dis.readInt();

			echoSocket.close();
		} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
		// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	   return ch;
   }
   Chunk ReceiveChunk()
   {
	   Chunk input = null;
	   try{
		   
		   echoSocket = new Socket(Hostname,Port);
		   DataInputStream dis = new DataInputStream(echoSocket.getInputStream());
		   
			ArrayList<Integer> buf = new ArrayList<Integer>();
			int ch;
			for(int i = 0;i<20;i++) {
				ch = dis.read();
				//if (ch == 0)	break;
				buf.add(ch);
			}
			Log.i("command:","for debug");
			byte[] bytes = new byte[buf.size()];

			for (int i = 0; i < buf.size(); i++)
			{
				bytes[i]= buf.get(i).byteValue();
			}
			Log.i("command:","for debug2");
			input = new Chunk(bytes);
		   
	   } catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
			// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	   return input;
   }
   class SocThread extends AsyncTask<Integer, Void, ArrayList<Chunk>>{
	   protected ArrayList<Chunk> doInBackground(Integer ...cmd) {
		   //Log.i("command:","command");
		   if(cmd[0]==1)
		   {
			   ArrayList<Chunk> info = new ArrayList();
			   byte command = 1;
			   SendByte(command);
			   info.add(new Chunk(ReceiveInt()));
			   return info;
		   }
		   else if(cmd[0]==2)
		   {
			   ArrayList<Chunk> info = new ArrayList();
			   byte command = 2;
			   SendByte(command);
			   info.add(new Chunk(ReceiveInt()));
			   return info;
		   }
		   else if(cmd[0]==3)
		   {
			   ArrayList<Chunk> info = new ArrayList();
			   byte command = 3;
			   SendByte(command);
			   Log.i("command:","set");
			   info.add(new Chunk(ReceiveInt()));
			   return info;
		   }
		   else if(cmd[0]==4)
		   {
			   
			   ArrayList<Chunk> info = new ArrayList();
			   byte command = 4;
			  SendByte(command);

			   int bag_num = ReceiveInt();
			   
			   
			   //info.add(ReceiveChunk());
//			   Log.i("command:",Integer.toString(bag_num));
//			   for(int i = 0;i<1;i++)
//			   {
//				   info.add(ReceiveChunk());
//			   }
//			   Log.i("command:",Integer.toString(info.size()));
			   return info;
			   
		   }
	   
		 return null;  
	       
	    }
	    
	    /** The system calls this to perform work in the UI thread and delivers
	      * the result from doInBackground() */
	    protected void onPostExecute(ArrayList<Chunk> result) {
	        Info = result;
	    }
    
    }
   
   
}