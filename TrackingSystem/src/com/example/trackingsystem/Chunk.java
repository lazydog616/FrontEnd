package com.example.trackingsystem;

import java.util.ArrayList;

import android.util.Log;

public class Chunk {
	
	double lat;
	double lon;
	double tem;
	int year;
	int month;
	int day;
	int hour;
	int min;
	int sec;
	int acce;
	int command;
	public Chunk(int cmd)
	{
		command = cmd; //only contains command info
		
	}
	byte[] input = new byte[20];
	public Chunk(byte[] bytes){
		input = bytes;
		byte sign;
		
		command = 0;//no command info contains
		//lat
		sign = bytes[0];//lat sign
		if(sign==0)
		{
			lat = bytes[1];// + bytes[2]/60 + bytes[3]/3600;
		}
		else
		{
			lat = bytes[1];// + bytes[2]/60 + bytes[3]/3600;
			lat = -lat;
		}
		//lon
		sign = bytes[5];
		if(sign==0)
		{
			lon = bytes[6];// + bytes[7]/60 + bytes[8]/3600;
		}
		else
		{
			lon = bytes[6];// + bytes[7]/60 + bytes[8]/3600;
			lon = -lon;
		}
		//time
		year = bytes[10];
		month = bytes[11];
		day = bytes[12];
		hour = bytes[13];
		min = bytes[14];
		sec = bytes[15];
		//temperature
		sign = bytes[16];
		if(sign==0)
		{
			tem = bytes[17] + bytes[18]/100;
		}
		else		
		{
			tem = -bytes[17] - bytes[18]/100;
		}
		//acce
		acce = bytes[19];
		
			
		
	}
	void PrintChunk()
	{
		Log.i("Chunk:","lat:" + Double.toString(lat) + 
				" lon:" + Double.toString(lon) + "\n" +
				" time:" + Integer.toString(year) + "/" +
				Integer.toString(month) + "/" + 
				Integer.toString(day) + "/" + 
				Integer.toString(hour) + "/" + 
				Integer.toString(min) + "/" + 
				Integer.toString(sec) + "/" + "\n" +
				"temperature: " + Double.toString(tem) + "\n" +
				"acce: " + Integer.toString(acce));
		
		
	}
	void PrintCommand()
	{
		Log.i("command:", "recieved" + Integer.toString(command));
	}
	String getTemAndAcce()
	{
		String TemAndAcce = null;
		TemAndAcce = "tem: " + Double.toString(tem) + "\nacce:" + Integer.toString(acce);
		return TemAndAcce;
	}
	
	
}
